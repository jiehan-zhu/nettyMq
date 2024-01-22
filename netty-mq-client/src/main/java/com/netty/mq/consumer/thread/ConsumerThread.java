package com.netty.mq.consumer.thread;

import com.netty.mq.MqMessage;
import com.netty.mq.broker.NettyMqBroker;
import com.netty.mq.consumer.NettyMqConsumer;
import com.netty.mq.consumer.MqResult;
import com.netty.mq.consumer.registry.ConsumerRegistryHelper;
import com.netty.mq.factory.NettyMqClientFactory;
import com.netty.mq.util.MqStatus;
import com.netty.mq.util.LogHelper;
import com.netty.mq.util.ThrowableUtil;
import com.xxl.rpc.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * client-消息核心线程
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class ConsumerThread extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

    private NettyMqConsumer consumerHandler;
    private com.netty.mq.consumer.annotation.MqConsumer mqConsumer;

    private String uuid;

    public ConsumerThread(NettyMqConsumer consumerHandler) {
        this.consumerHandler = consumerHandler;
        this.mqConsumer = consumerHandler.getClass().getAnnotation(com.netty.mq.consumer.annotation.MqConsumer.class);
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public com.netty.mq.consumer.annotation.MqConsumer getMqConsumer() {
        return mqConsumer;
    }
    public String getUuid() {
        return uuid;
    }

    @Override
    public void run() {

        int waitTim = 0;
        while (!NettyMqClientFactory.clientFactoryPoolStoped) {
            try {
                // check active
                ConsumerRegistryHelper.ActiveInfo activeInfo = NettyMqClientFactory.getConsumerRegistryHelper().isActice(this);
                logger.debug(">>>>>>>>>>> xxl-mq, consumer active check, topic:{}, group:{}, ActiveInfo={}", mqConsumer.topic(), mqConsumer.group(), activeInfo);

                if (activeInfo != null) {
//                    logger.info(">>>>>>>>>>>>> "+activeInfo+ "<<<<<<<<<<<<<<<<<<<");

                    int pageSize = 100;

                    // pullNewMessage
                    NettyMqBroker broker = NettyMqClientFactory.getXxlMqBroker();
                    List<MqMessage> messageList = broker.pullNewMessage(mqConsumer.topic(), mqConsumer.group(), activeInfo.rank, activeInfo.total, pageSize);

                    if (messageList != null && messageList.size() > 0) {
                        logger.info(">>>>>>>>>>>>> "+consumerHandler.getClass().getName() + "start <<<<<<<<<<<<<<<<<<<");
                        logger.info(mqConsumer.topic() + " messageListSize = " + messageList.size());
                        // reset wait time
                        if (mqConsumer.transaction()) {
                            waitTim = 0;    // transaction message status timely updated by lock, will not repeat pull
                        } else {
                            waitTim = 1;    // no-transaction message status delay updated by callback, may be repeat, need wail for callback
                        }

                        for (final MqMessage msg : messageList) {
                            //check  consumer is running
                            if(!NettyMqClientFactory.checkConsumerRunning(uuid + mqConsumer.topic() + mqConsumer.group())){
                                break;
                            }
//                            if (NettyMqClientFactory.mqMessageStop){
//                                logger.info(">>>>>>> "+consumerHandler.getClass().getName() + " -- " +mqConsumer.topic()+" stop !!!!!!! >>>>>>>");
//                                continue;
//                            }
                            // check active twice
                            ConsumerRegistryHelper.ActiveInfo newActiveInfo = NettyMqClientFactory.getConsumerRegistryHelper().isActice(this);
                            if (!(newActiveInfo != null && newActiveInfo.rank == activeInfo.rank && newActiveInfo.total == activeInfo.total)) {
                                break;
                            }

                            // lock message, for transaction
                            if (mqConsumer.transaction()) {
                                String appendLog_lock = LogHelper.makeLog(
                                        "锁定消息",
                                        ("消费者信息="+ newActiveInfo
                                                +"；<br>消费者IP="+IpUtil.getIp())
                                );
                                int lockRet = NettyMqClientFactory.getXxlMqBroker().lockMessage(msg.getId(), appendLog_lock);
                                if (lockRet < 1) {
                                    continue;
                                }
                            }

                            // consume message
                            MqResult mqResult;
                            try {

                                if (msg.getTimeout() > 0) {
                                    // limit timeout
                                    Thread futureThread = null;
                                    try {
                                        FutureTask<MqResult> futureTask = new FutureTask<>(() -> consumerHandler.consume(msg));
                                        futureThread = new Thread(futureTask);
                                        futureThread.start();

                                        mqResult = futureTask.get(msg.getTimeout(), TimeUnit.SECONDS);
                                    } catch (TimeoutException e) {
                                        logger.error(e.getMessage(), e);
                                        mqResult = new MqResult(MqResult.FAIL_CODE, "Timeout:" + e.getMessage());
                                    } finally {
                                        futureThread.interrupt();
                                    }
                                } else {
                                    // direct run
                                    mqResult = consumerHandler.consume(msg);
                                }

                                if (mqResult == null) {
                                    mqResult = MqResult.FAIL;
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                                String errorMsg = ThrowableUtil.toString(e);
                                mqResult = new MqResult(MqResult.FAIL_CODE, errorMsg);
                            }

                            // log
                            String appendLog_consume;
                            if (mqConsumer.transaction()) {
                                appendLog_consume = LogHelper.makeLog(
                                        "消费消息",
                                        ("消费结果="+(mqResult.isSuccess()?"成功":"失败")
                                                +"；<br>消费日志="+mqResult.getLog())
                                );
                            } else {
                                appendLog_consume = LogHelper.makeLog(
                                        "消费消息",
                                        ("消费结果="+(mqResult.isSuccess()?"成功":"失败")
                                                +"；<br>消费者信息="+ activeInfo
                                                +"；<br>消费者IP="+IpUtil.getIp()
                                                +"；<br>消费日志="+mqResult.getLog())
                                );
                            }

                            // callback
                            msg.setStatus(mqResult.isSuccess()? MqStatus.SUCCESS.name(): MqStatus.FAIL.name());
                            msg.setLog(appendLog_consume);
                            NettyMqClientFactory.callbackMessage(msg);

//                            logger.info(">>>>>>>>>>> xxl-mq, consumer finish,  topic:{}, group:{}, ActiveInfo={}", mqConsumer.topic(), mqConsumer.group(), activeInfo.toString());
                        }
                        logger.info(">>>>>>>>>>>>> "+consumerHandler.getClass().getName() + "end <<<<<<<<<<<<<<<<<<<");

                    } else {
                        Calendar calendar = Calendar.getInstance();
                        int hour24 = calendar.get(Calendar.HOUR_OF_DAY);
                        // 白天提起精神,
                        int step = 1, max = 5;
                        // 半夜(1-6点)可以偷着睡.
                        if (1 < hour24 && hour24 < 6) {
                            step = 2;
                            max = 60;
                        }
                        waitTim = Math.min((waitTim + step), max);
//                        waitTim = (waitTim+10)<=60?(waitTim+10):60;
                    }
                } else {
                    waitTim = 2;
                }

            } catch (Exception e) {
                if (!NettyMqClientFactory.clientFactoryPoolStoped) {
                    logger.error(e.getMessage(), e);
                }
            }

            // wait
            try {
                TimeUnit.SECONDS.sleep(waitTim);
            } catch (Exception e) {
                if (!NettyMqClientFactory.clientFactoryPoolStoped) {
                    logger.error(e.getMessage(), e);
                }
            }

        }
    }
}
