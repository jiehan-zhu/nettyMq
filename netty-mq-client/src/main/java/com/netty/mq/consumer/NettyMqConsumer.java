package com.netty.mq.consumer;

import com.netty.mq.MqMessage;

/**
 * Created by jiehan-zhu on 24/01/28.
 */
public interface NettyMqConsumer {

    /**
     * consume message
     *
     * @param data
     * @return
     * @throws Exception
     */
     MqResult consume(MqMessage data) throws Exception;

}
