//package com.pearadmin.test;
//
//
//import com.netty.mq.MqMessage;
//import com.netty.mq.consumer.IMqConsumer;
//import com.netty.mq.consumer.MqResult;
//import com.netty.mq.consumer.annotation.MqConsumer;
//import org.springframework.stereotype.Component;
//
//@Component
//@MqConsumer(group = "PushWarehouse", topic = "push-SLM")
//public class TestConsumer implements IMqConsumer {
//
//
//    @Override
//    public MqResult consume(MqMessage data) throws Exception {
//
//        System.out.println("test-------------------");
//
//
//        return new MqResult(MqResult.SUCCESS_CODE);
//    }
//}
