package com.netty.mq.demo.controller;

import com.netty.mq.consumer.NettyMqConsumer;
import com.netty.mq.consumer.registry.ConsumerRegistryHelper;
import com.netty.mq.consumer.thread.ConsumerThread;
import com.netty.mq.demo.utils.SpringBeanUtil;
import com.netty.mq.factory.NettyMqClientFactory;
import com.netty.mq.util.MqStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 祝杰汉
 * 对服务端开放的 消费者管理功能
 * 2024-01-12
 */
@RestController
public class ConsumerController {
    //MqLog专用日志打印
    private static final Logger log = LoggerFactory.getLogger("MqLog");

    /**
     * 根据消费者的全限定类名 在线添加消费者
     *
     * @param consumerClassName 消费者全限定类名 例如 com.netty.mq.demo.consumer.TestConsumer
     * @param addNum            要添加的消费者数量
     * @return true 添加成功 false 添加失败
     */
    @RequestMapping("/consumerAdd")
    public boolean consumerAdd(String consumerClassName, int addNum) {
        log.info("消费者创建请求开始consumerClassName[{}] addNum[{}]", consumerClassName, addNum);
        //初始化消费者集合
        List<ConsumerThread> consumerRespository = new ArrayList<>(addNum);
        try {
            //获取class类型
            Class<?> aClass = Class.forName(consumerClassName);
            for (int i = 0; i < addNum; i++) {
                consumerRespository.add(new ConsumerThread((NettyMqConsumer) SpringBeanUtil.getBean(aClass)));
            }
            //根据xxl-mq-client对外提供的消费者注册类 ConsumerRegistryHelper 注册需要新增的消费者
            ConsumerRegistryHelper.registerConsumer(consumerRespository);
            for (ConsumerThread item : consumerRespository) {
                NettyMqClientFactory.getClientFactoryThreadPool().execute(item);
            }
            //此处为了便于统一进行管理 添加到ConsumerRespository中
            NettyMqClientFactory.getConsumerRespository().addAll(consumerRespository);
        } catch (Exception e) {
            log.error("消费者在线创建失败", e);
            return false;
        }
        log.info("消费者创建成功consumerClassName[{}] addNum[{}]", consumerClassName, addNum);
        return true;
    }

    /**
     * 消费者启停停
     *
     * @param consumerKey 需要暂停的消费者key
     * @param status 状态
     * @return true 暂停成功 false 暂停失败
     */
    @RequestMapping("/consumerStatus")
    public boolean consumerStop(@RequestParam String consumerKey,String status) {
        log.info("消费者{}请求 consumerKey[{}]", status, consumerKey);
        if (MqStatus.RUNNING.name().equals(status)){
            return NettyMqClientFactory.consumerRun(consumerKey);
        }else {
            return NettyMqClientFactory.consumerStop(consumerKey);
        }
    }

    /**
     * 消费者批量暂停
     *
     * @param consumerKeyList 需要暂停的消费者key集合
     * @return true 暂停成功 false 暂停失败
     */
    @RequestMapping("/consumerBatchStop")
    public boolean consumerBatchStop(@RequestBody List<String> consumerKeyList) {
        log.info("消费者批量暂停 consumerKeyList[{}]", consumerKeyList.toString());
        return NettyMqClientFactory.consumerBatchStop(consumerKeyList);
    }

    /**
     * 消费者批量唤醒
     *
     * @param consumerKeyList 需要唤醒的消费者key集合
     * @return true 唤醒成功 false 唤醒失败
     */
    @RequestMapping("/consumerBatchRun")
    public boolean consumerBatchRun(@RequestBody List<String> consumerKeyList) {
        log.info("消费者批量唤醒 consumerKeyList[{}]", consumerKeyList.toString());
        return NettyMqClientFactory.consumerBatchRun(consumerKeyList);
    }

}
