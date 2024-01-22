package com.netty.mq.factory.impl;

import com.netty.mq.consumer.NettyMqConsumer;
import com.netty.mq.factory.NettyMqClientFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * spring注册工厂
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class NettyMqSpringClientFactory implements ApplicationContextAware, DisposableBean {

    // ---------------------- param  ----------------------

    private String adminAddress;
    private String accessToken;

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // NettyMqClientFactory
    public NettyMqClientFactory nettyMqClientFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        // load consumer from spring
        List<NettyMqConsumer> consumerList = new ArrayList<>();

        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(com.netty.mq.consumer.annotation.MqConsumer.class);
        if (serviceMap.size()>0) {
            for (Object serviceBean : serviceMap.values()) {
                if (serviceBean instanceof NettyMqConsumer) {
                    consumerList.add((NettyMqConsumer) serviceBean);
                }
            }
        }

        // init
        nettyMqClientFactory = new NettyMqClientFactory();

        nettyMqClientFactory.setAdminAddress(adminAddress);
        nettyMqClientFactory.setAccessToken(accessToken);
        nettyMqClientFactory.setConsumerList(consumerList);

        nettyMqClientFactory.init();
    }

    @Override
    public void destroy() throws Exception {
        nettyMqClientFactory.destroy();
    }

}
