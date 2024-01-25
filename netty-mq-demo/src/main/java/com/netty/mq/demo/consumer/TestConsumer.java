package com.netty.mq.demo.consumer;



import com.netty.mq.MqMessage;
import com.netty.mq.consumer.MqResult;
import com.netty.mq.consumer.NettyMqConsumer;
import com.netty.mq.consumer.annotation.MqConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 祝杰汉
 * 模拟消费者
 * 2024-01-12
 */
@Component
@MqConsumer(group = "test", topic = "test")
public class TestConsumer implements NettyMqConsumer {
    private static final Logger log = LoggerFactory.getLogger("MqLog");


    @Override
    public MqResult consume(MqMessage data) throws Exception {

        //模拟消费消息
        log.info(data.getData());
        Thread.sleep(5000);

        return new MqResult(MqResult.SUCCESS_CODE);
    }
}
