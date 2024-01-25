package com.netty.mq.demo.controller;


import com.netty.mq.MqMessage;
import com.netty.mq.producer.NettyMqProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author 祝杰汉
 * 模拟发送消息
 * 2024-01-12
 */
@Controller
public class MessageController {



	@RequestMapping("send")
	public String send(){

		MqMessage message = new MqMessage();
		message.setTopic("test");
		message.setGroup("test");
		message.setData("test");
		NettyMqProducer.produce(message);

		return "OK";
	}



}
