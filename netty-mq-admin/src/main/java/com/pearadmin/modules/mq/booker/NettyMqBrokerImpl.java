//package com.pearadmin.modules.mq.booker;
//
//import com.netty.mq.MqMessage;
//import com.netty.mq.broker.NettyMqBroker;
//import com.netty.mq.model.MqCommonRegistryData;
//import com.netty.mq.service.impl.MqCommonRegistryServiceImpl;
//import com.netty.mq.util.LogHelper;
//import com.netty.mq.util.MqStatus;
//import com.pearadmin.modules.mq.mapper.MqMessageMapper;
//import com.xxl.rpc.remoting.net.NetEnum;
//import com.xxl.rpc.remoting.provider.XxlRpcProviderFactory;
//import com.xxl.rpc.serialize.Serializer;
//import com.xxl.rpc.util.IpUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.*;
//
///**
// * Created by xuxueli on 16/8/28.
// */
//@Component
//public class NettyMqBrokerImpl implements NettyMqBroker, InitializingBean, DisposableBean {
//    private final static Logger logger = LoggerFactory.getLogger(NettyMqBrokerImpl.class);
//
//
//    // ---------------------- param ----------------------
//
//    @Value("${netty.mq.rpc.remoting.ip}")
//    private String ip;
//
//    @Value("${netty.mq.rpc.remoting.port}")
//    private int port;
//
//    @Resource
//    private MqMessageMapper mqMessageDao;
//
//
//    // ---------------------- broker server ----------------------
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // init server
//        initServer();
//
//        // init thread
//        initThead();
//    }
//
//    @Override
//    public void destroy() throws Exception {
//
//        // destory server
//        destoryServer();
//
//        // destory thread
//        destroyThread();
//    }
//
//    // ---------------------- broker thread ----------------------
//
//    private final LinkedBlockingQueue<MqMessage> newMessageQueue = new LinkedBlockingQueue<>();
//    private final LinkedBlockingQueue<MqMessage> callbackMessageQueue = new LinkedBlockingQueue<>();
//    private final Map<String, Long> alarmMessageInfo = new ConcurrentHashMap<>();
//
//    private final ExecutorService executorService = Executors.newCachedThreadPool();
//    private volatile boolean executorStoped = false;
//
//    public void initThead() throws Exception {
//
//        /**
//         * async save message, mult thread  (by event)
//         */
//        for (int i = 0; i < 3; i++) {
//            executorService.execute(() -> {
//                while (!executorStoped) {
//                    try {
//                        MqMessage message = newMessageQueue.take();
//                        // load
//                        List<MqMessage> messageList = new ArrayList<>();
//                        messageList.add(message);
//
//                        List<MqMessage> otherMessageList = new ArrayList<>();
//                        int drainToNum = newMessageQueue.drainTo(otherMessageList, 100);
//                        if (drainToNum > 0) {
//                            messageList.addAll(otherMessageList);
//                        }
//
//                        // save
//                        mqMessageDao.save(messageList);
//                    } catch (Exception e) {
//                        if (!executorStoped) {
//                            logger.error(e.getMessage(), e);
//                        }
//                    }
//                }
//
//                // end save
//                List<MqMessage> otherMessageList = new ArrayList<>();
//                int drainToNum = newMessageQueue.drainTo(otherMessageList);
//                if (drainToNum> 0) {
//                    mqMessageDao.save(otherMessageList);
//                }
//
//            });
//        }
//
//        /**
//         * async callback message, mult thread  (by event)
//         */
//        for (int i = 0; i < 3; i++) {
//            executorService.execute(() -> {
//                while (!executorStoped) {
//                    try {
//                        MqMessage message = callbackMessageQueue.take();
//                        // load
//                        List<MqMessage> messageList = new ArrayList<>();
//                        messageList.add(message);
//
//                        List<MqMessage> otherMessageList = new ArrayList<>();
//                        int drainToNum = callbackMessageQueue.drainTo(otherMessageList, 100);
//                        if (drainToNum > 0) {
//                            messageList.addAll(otherMessageList);
//                        }
//
//                        // save
//                        mqMessageDao.updateStatus(messageList);
//
//                        // fill alarm info
//                        for (MqMessage alarmItem: messageList) {
//                            if (MqStatus.FAIL.name().equals(alarmItem.getStatus())) {
//                                Long failCount = alarmMessageInfo.get(alarmItem.getTopic());
//                                failCount = failCount!=null?failCount++:1;
//                                alarmMessageInfo.put(alarmItem.getTopic(), failCount);
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        if (!executorStoped) {
//                            logger.error(e.getMessage(), e);
//                        }
//                    }
//                }
//
//                // end save
//                List<MqMessage> otherMessageList = new ArrayList<>();
//                int drainToNum = callbackMessageQueue.drainTo(otherMessageList);
//                if (drainToNum > 0) {
//                    mqMessageDao.updateStatus(otherMessageList);
//                }
//
//            });
//        }
//
//
//        /**
//         * auto retry message "retryCount-1 + status change"  (by cycle, 1/60s)
//         *
//         * auto reset block timeout message "check block + status change"  (by cycle, 1/60s)
//         */
//        executorService.execute(() -> {
//            while (!executorStoped) {
//                try {
//                    // mult retry message
//                    String appendLog = LogHelper.makeLog("失败重试", "状态自动还原,剩余重试次数减一");
//                    int count = mqMessageDao.updateRetryCount(MqStatus.FAIL.name(), MqStatus.NEW.name(), appendLog);
//                    if (count > 0) {
//                        logger.info("Netty-mq, retry message, count:{}", count);
//                    }
//                } catch (Exception e) {
//                    if (!executorStoped) {
//                        logger.error(e.getMessage(), e);
//                    }
//                }
//                try {
//                    // mult reset block message
//                    String appendLog = LogHelper.makeLog("阻塞清理", "状态自动标记失败");
//                    int count = mqMessageDao.resetBlockTimeoutMessage(MqStatus.RUNNING.name(), MqStatus.FAIL.name(), appendLog);
//                    if (count > 0) {
//                        logger.info("Netty-mq, retry block message, count:{}", count);
//                    }
//                } catch (Exception e) {
//                    if (!executorStoped) {
//                        logger.error(e.getMessage(), e);
//                    }
//                }
//                try {
//                    // sleep
//                    TimeUnit.SECONDS.sleep(60);
//                } catch (Exception e) {
//                    if (!executorStoped) {
//                        logger.error(e.getMessage(), e);
//                    }
//                }
//            }
//        });
//
//
//    }
//    public void destroyThread(){
//        executorStoped = true;
//        executorService.shutdownNow();
//    }
//
//
//    // ---------------------- broker server ----------------------
//
//    private XxlRpcProviderFactory providerFactory;
//
//    public void initServer() throws Exception {
//
//        // address, static registry
//        ip = (ip!=null&&ip.trim().length()>0)?ip:IpUtil.getIp();
//        String address = IpUtil.getIpPort(ip, port);
//
//        MqCommonRegistryData MqCommonRegistryData = new MqCommonRegistryData();
//        MqCommonRegistryData.setKey(NettyMqBroker.class.getName());
//        MqCommonRegistryData.setValue(address);
//        MqCommonRegistryServiceImpl.staticRegistryData = MqCommonRegistryData;
//
//
//        // init server
//        providerFactory = new XxlRpcProviderFactory();
//        providerFactory.initConfig(NetEnum.NETTY, Serializer.SerializeEnum.HESSIAN.getSerializer(), ip, port, null, null, null);
//
//        // add server
//        providerFactory.addService(NettyMqBroker.class.getName(), null, this);
//
//        // start server
//        providerFactory.start();
//    }
//
//    public void destoryServer() throws Exception {
//        // stop server
//        if (providerFactory != null) {
//            providerFactory.stop();
//        }
//    }
//
//
//    // ---------------------- broker api ----------------------
//
//    @Override
//    public int addMessages(List<MqMessage> messages) {
//        newMessageQueue.addAll(messages);
//        return messages.size();
//    }
//
//    @Override
//    public List<MqMessage> pullNewMessage(String topic, String group, int consumerRank, int consumerTotal, int pagesize) {
//        return mqMessageDao.pullNewMessage(MqStatus.NEW.name(), topic, group, consumerRank, consumerTotal, pagesize);
//    }
//
//    @Override
//    public int lockMessage(long id, String appendLog) {
//        return mqMessageDao.lockMessage(id, appendLog, MqStatus.NEW.name(), MqStatus.RUNNING.name());
//    }
//
//    @Override
//    public int callbackMessages(List<MqMessage> messages) {
//        callbackMessageQueue.addAll(messages);
//        return messages.size();
//    }
//
//}
