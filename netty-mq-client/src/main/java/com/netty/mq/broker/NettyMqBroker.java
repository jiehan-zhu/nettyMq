package com.netty.mq.broker;

import com.netty.mq.MqMessage;

import java.util.List;

/**
 * Created by jiehan-zhu on 24/01/28.
 */
public interface NettyMqBroker {

    /**
     * 新增消息，批量
     *
     * @param messages
     * @return
     */
     int addMessages(List<MqMessage> messages);

    /**
     * 分片数据，批量： MOD( "分片ID", #{consumerTotal}) = #{consumerRank}, 值 consumerTotal>1 时生效
     */
     List<MqMessage> pullNewMessage(String topic, String group, int consumerRank, int consumerTotal, int pagesize);

    /**
     *  锁定消息，单个；MqStatus：NEW >>> RUNNING
     *
     *  @param id
     *  @param appendLog
     *  @return
     */
     int lockMessage(long id, String appendLog);

    /**
     *  回调消息，批量；MqStatus：RUNNING >>> SUCCESS/FAIL
     *
     * @param messages
     * @return
     */
     int callbackMessages(List<MqMessage> messages);

}
