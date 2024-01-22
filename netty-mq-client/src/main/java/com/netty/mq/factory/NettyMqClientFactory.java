package com.netty.mq.factory;

import com.netty.mq.MqMessage;
import com.netty.mq.broker.NettyMqBroker;
import com.netty.mq.consumer.NettyMqConsumer;
import com.netty.mq.consumer.registry.ConsumerRegistryHelper;
import com.netty.mq.consumer.thread.ConsumerThread;
import com.xxl.rpc.registry.impl.XxlRegistryServiceRegistry;
import com.xxl.rpc.remoting.invoker.XxlRpcInvokerFactory;
import com.xxl.rpc.remoting.invoker.call.CallType;
import com.xxl.rpc.remoting.invoker.reference.XxlRpcReferenceBean;
import com.xxl.rpc.remoting.invoker.route.LoadBalance;
import com.xxl.rpc.remoting.net.NetEnum;
import com.xxl.rpc.serialize.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 注册工厂 初始化
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@SuppressWarnings("unchecked")
public class NettyMqClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(NettyMqClientFactory.class);


    // ---------------------- param  ----------------------

    private String adminAddress;
    private String accessToken;
    public List<NettyMqConsumer> consumerList;

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setConsumerList(List<NettyMqConsumer> consumerList) {
        this.consumerList = consumerList;
    }

    // ---------------------- init destroy  ----------------------

    public void init() {

        // pre : valid consumer
        validConsumer();


        // start BrokerService
        startBrokerService();

        // start consumer
        startConsumer();

    }

    public void destroy() throws Exception {

        // pre : destory ClientFactoryThreadPool
        destoryClientFactoryThreadPool();


        // destory Consumer
        destoryConsumer();

        // destory BrokerService
        destoryBrokerService();

    }


    // ---------------------- thread pool ----------------------

    private static ExecutorService clientFactoryThreadPool = Executors.newCachedThreadPool();

    public static ExecutorService getClientFactoryThreadPool(){
        return clientFactoryThreadPool;
    }

    public static volatile boolean clientFactoryPoolStoped = false;


    /**
     * destory consumer thread
     */
    private void destoryClientFactoryThreadPool(){
        clientFactoryPoolStoped = true;
        clientFactoryThreadPool.shutdownNow();
    }


    // ---------------------- broker service ----------------------

    private XxlRpcInvokerFactory xxlRpcInvokerFactory = null;

    private static NettyMqBroker xxlMqBroker;
    private static ConsumerRegistryHelper consumerRegistryHelper = null;
    private static LinkedBlockingQueue<MqMessage> newMessageQueue = new LinkedBlockingQueue<>();
    private static LinkedBlockingQueue<MqMessage> callbackMessageQueue = new LinkedBlockingQueue<>();

    public static NettyMqBroker getXxlMqBroker() {
        return xxlMqBroker;
    }
    public static ConsumerRegistryHelper getConsumerRegistryHelper() {
        return consumerRegistryHelper;
    }
    public static void addMessages(MqMessage mqMessage, boolean async){
        if (async) {
            // async queue, mult send
            newMessageQueue.add(mqMessage);
        } else {
            // sync rpc, one send
            xxlMqBroker.addMessages(Arrays.asList(mqMessage));
        }

    }
    public static void callbackMessage(MqMessage mqMessage){
        callbackMessageQueue.add(mqMessage);
    }

    public void startBrokerService() {
        // init XxlRpcInvokerFactory
        xxlRpcInvokerFactory = new XxlRpcInvokerFactory(XxlRegistryServiceRegistry.class, new HashMap<String, String>(){{
            put(XxlRegistryServiceRegistry.XXL_REGISTRY_ADDRESS, adminAddress);
            put(XxlRegistryServiceRegistry.ACCESS_TOKEN, accessToken);
        }});
        try {
            xxlRpcInvokerFactory.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // init ConsumerRegistryHelper
        XxlRegistryServiceRegistry commonServiceRegistry = (XxlRegistryServiceRegistry) xxlRpcInvokerFactory.getServiceRegistry();
        consumerRegistryHelper = new ConsumerRegistryHelper(commonServiceRegistry);

        // init NettyMqBroker
        xxlMqBroker = (NettyMqBroker) new XxlRpcReferenceBean(
                NetEnum.NETTY,
                Serializer.SerializeEnum.HESSIAN.getSerializer(),
                CallType.SYNC,
                LoadBalance.ROUND,
                NettyMqBroker.class,
                null,
                10000,
                null,
                null,
                null,
                xxlRpcInvokerFactory).getObject();

        // async + mult, addMessages
        for (int i = 0; i < 3; i++) {
            clientFactoryThreadPool.execute(() -> {

                while (!NettyMqClientFactory.clientFactoryPoolStoped) {
                    try {
                        MqMessage message = newMessageQueue.take();
                        if (message != null) {
                            // load
                            List<MqMessage> messageList = new ArrayList<>();
                            messageList.add(message);

                            List<MqMessage> otherMessageList = new ArrayList<>();
                            int drainToNum = newMessageQueue.drainTo(otherMessageList, 100);
                            if (drainToNum > 0) {
                                messageList.addAll(otherMessageList);
                            }

                            // save
                            xxlMqBroker.addMessages(messageList);
                        }
                    } catch (Exception e) {
                        if (!NettyMqClientFactory.clientFactoryPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

                // finally total
                List<MqMessage> otherMessageList = new ArrayList<>();
                int drainToNum = newMessageQueue.drainTo(otherMessageList);
                if (drainToNum> 0) {
                    xxlMqBroker.addMessages(otherMessageList);
                }

            });
        }

        // async + mult, addMessages
        for (int i = 0; i < 3; i++) {
            clientFactoryThreadPool.execute(() -> {

                while (!NettyMqClientFactory.clientFactoryPoolStoped) {
                    try {
                        MqMessage message = callbackMessageQueue.take();
                        if (message != null) {
                            // load
                            List<MqMessage> messageList = new ArrayList<>();
                            messageList.add(message);

                            List<MqMessage> otherMessageList = new ArrayList<>();
                            int drainToNum = callbackMessageQueue.drainTo(otherMessageList, 100);
                            if (drainToNum > 0) {
                                messageList.addAll(otherMessageList);
                            }

                            // callback
                            xxlMqBroker.callbackMessages(messageList);
                        }
                    } catch (Exception e) {
                        if (!NettyMqClientFactory.clientFactoryPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

                // finally total
                List<MqMessage> otherMessageList = new ArrayList<>();
                int drainToNum = callbackMessageQueue.drainTo(otherMessageList);
                if (drainToNum> 0) {
                    xxlMqBroker.callbackMessages(otherMessageList);
                }

            });
        }


    }
    public void destoryBrokerService() throws Exception {
        // stop invoker factory
        if (xxlRpcInvokerFactory != null) {
            xxlRpcInvokerFactory.stop();
        }
    }



    // ---------------------- queue consumer ----------------------

    // queue consumer respository
    public static List<ConsumerThread> consumerRespository = new ArrayList<ConsumerThread>();

    /**
     * 对外暴露当前消费者集合 后续添加的消费者统一在本集合中进行管理
     * @return consumerRespository
     */
    public static List<ConsumerThread> getConsumerRespository(){
        return consumerRespository;
    }

    public void validConsumer(){
        // valid
        if (consumerList==null || consumerList.size()==0) {
            logger.warn(">>>>>>>>>>> xxl-mq, NettyMqConsumer not found.");
            return;
        }

        // make ConsumerThread
        for (NettyMqConsumer consumer : consumerList) {
            logger.info(">>>>>>>>>>> xxl-mq, NettyMqConsumer:{}",consumer.getClass());

            // valid annotation
            com.netty.mq.consumer.annotation.MqConsumer annotation = consumer.getClass().getAnnotation(com.netty.mq.consumer.annotation.MqConsumer.class);
            if (annotation == null) {
                throw new RuntimeException("xxl-mq, NettyMqConsumer("+ consumer.getClass() +"), annotation is not exists.");
            }

            // valid group
            if (annotation.group()==null || annotation.group().trim().length()==0) {
                // empty group means consume broadcase message, will replace by uuid
                try {
                    // annotation memberValues
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
                    Field mValField = invocationHandler.getClass().getDeclaredField("memberValues");
                    mValField.setAccessible(true);
                    Map memberValues = (Map) mValField.get(invocationHandler);

                    // set data for "group"
                    String randomGroup = UUID.randomUUID().toString().replaceAll("-", "");
                    memberValues.put("group", randomGroup);
                } catch (Exception e) {
                    throw new RuntimeException("xxl-mq, NettyMqConsumer("+ consumer.getClass() +"), group empty and genereta error.");
                }

            }
            if (annotation.group()==null || annotation.group().trim().length()==0) {
                throw new RuntimeException("xxl-mq, NettyMqConsumer("+ consumer.getClass() +"),group is empty.");
            }

            // valid topic
            if (annotation.topic()==null || annotation.topic().trim().length()==0) {
                throw new RuntimeException("xxl-mq, NettyMqConsumer("+ consumer.getClass() +"), topic is empty.");
            }

            // consumer map
            consumerRespository.add(new ConsumerThread(consumer));
        }
    }

    private void startConsumer() {

        // valid
        if (consumerRespository ==null || consumerRespository.size()==0) {
            return;
        }

        // registry consumer
        getConsumerRegistryHelper().registerConsumer(consumerRespository);

        // execute thread
        for (ConsumerThread item: consumerRespository) {
            clientFactoryThreadPool.execute(item);
            logger.info(">>>>>>>>>>> xxl-mq, consumer init success, , topic:{}, group:{}", item.getMqConsumer().topic(), item.getMqConsumer().group());
        }

    }
    private void destoryConsumer(){

        // valid
        if (consumerRespository ==null || consumerRespository.size()==0) {
            return;
        }

        // stop registry consumer
        getConsumerRegistryHelper().removeConsumer(consumerRespository);

    }

    //消费者本地缓存 存储暂停的消费者
    private static List<String> consumerStopCache = new ArrayList<>();

    /**
     * 消费者暂停
     *
     * @param consumerKey uuid+topic+group
     */
    public static boolean consumerStop(String consumerKey) {
        return consumerStopCache.add(consumerKey);
    }

    /**
     * 消费者批量暂停
     *
     * @param consumerKeyList 批量暂停消费者集合
     * @return
     */
    public static boolean consumerBatchStop(List<String> consumerKeyList) {
        return consumerStopCache.addAll(consumerKeyList);
    }

    /**
     * 消费者唤醒
     *
     * @param consumerKey uuid+topic+group
     */
    public static boolean consumerRun(String consumerKey) {
        return consumerStopCache.remove(consumerKey);
    }

    /**
     * 消费者批量唤醒
     *
     * @param consumerKeyList 批量唤醒消费者集合
     * @return
     */
    public static boolean consumerBatchRun(List<String> consumerKeyList) {
        return consumerStopCache.removeAll(consumerKeyList);
    }

    /**
     * 检测消费者是否是运行状态
     *
     * @param consumerKey uuid+topic+group
     * @return true 运行态  false 暂停态
     */
    public static boolean checkConsumerRunning(String consumerKey) {
        if (consumerStopCache.contains(consumerKey)) {
            return false;
        }
        return true;
    }

}
