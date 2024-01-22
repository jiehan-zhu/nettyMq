package com.netty.mq.producer;

import com.netty.mq.MqMessage;
import com.netty.mq.consumer.annotation.MqConsumer;
import com.netty.mq.factory.NettyMqClientFactory;
import com.netty.mq.util.MqStatus;
import com.netty.mq.util.LogHelper;
import com.xxl.rpc.util.IpUtil;

import java.util.Set;

/**
 * Created by jiehan-zhu on 24/01/28.
 */
public class NettyMqProducer {

    // ---------------------- valid message ----------------------

    /**
     * valid message
     *
     * @param mqMessage
     * @return
     */
    public static void validMessage(MqMessage mqMessage){
        if (mqMessage == null) {
            throw new IllegalArgumentException("xxl-mq, MqMessage can not be null.");
        }

        // topic
        if (mqMessage.getTopic()==null || mqMessage.getTopic().trim().length()==0) {
            throw new IllegalArgumentException("xxl-mq, topic empty.");
        }
        if (!(mqMessage.getTopic().length()>=4 && mqMessage.getTopic().length()<=255)) {
            throw new IllegalArgumentException("xxl-mq, topic length invalid[4~255].");
        }

        // group
        if (mqMessage.getGroup()==null || mqMessage.getGroup().trim().length()==0) {
            mqMessage.setGroup(MqConsumer.DEFAULT_GROUP);
        }
        if (!(mqMessage.getGroup().length()>=4 && mqMessage.getGroup().length()<=255)) {
            throw new IllegalArgumentException("xxl-mq, group length invalid[4~255].");
        }

        // data
        if (mqMessage.getData() == null) {
            mqMessage.setData("");
        }
        if (mqMessage.getData().length() > 60000) {
            throw new IllegalArgumentException("xxl-mq, data length invalid[0~60000].");
        }

        // status
        mqMessage.setStatus(MqStatus.NEW.name());

//        // retryCount
//        if (mqMessage.getRetryCount() < 0) {
//            mqMessage.setRetryCount(0);
//        }
//
//        // shardingId
//        if (mqMessage.getShardingId() < 0) {
//            mqMessage.setShardingId(0);
//        }
//
//        // delayTime
//        if (mqMessage.getEffectTime() == null) {
//            mqMessage.setEffectTime(new Date());
//        }
//
//        // timeout
//        if (mqMessage.getTimeout() < 0) {
//            mqMessage.setTimeout(0);
//        }

        // log
        String appendLog = LogHelper.makeLog("生产消息", "消息生产者IP=" + IpUtil.getIp() );
        mqMessage.setLog(appendLog);
    }


    // ---------------------- produce message ----------------------

    /**
     * produce produce
     */
    public static void produce(MqMessage mqMessage, boolean async){
        // valid
        validMessage(mqMessage);

        // send
        NettyMqClientFactory.addMessages(mqMessage, async);
    }

    public static void produce(MqMessage mqMessage){
        produce(mqMessage, true);
    }


    // ---------------------- broadcast message ----------------------

    /**
     * broadcast produce
     */
    public static void broadcast(MqMessage mqMessage, boolean async){
        // valid
        validMessage(mqMessage);

        // find online group
        Set<String> groupList = NettyMqClientFactory.getConsumerRegistryHelper().getTotalGroupList(mqMessage.getTopic());

        // broud total online group
        for (String group: groupList) {

            // clone msg
            MqMessage cloneMsg = new MqMessage(mqMessage);
            cloneMsg.setGroup(group);

            // produce clone msg
            produce(cloneMsg, true);
        }
    }

    public static void broadcast(MqMessage mqMessage){
        broadcast(mqMessage, true);
    }

}
