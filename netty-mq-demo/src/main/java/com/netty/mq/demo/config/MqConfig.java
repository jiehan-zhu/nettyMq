package com.netty.mq.demo.config;
import com.netty.mq.factory.impl.NettyMqSpringClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 祝杰汉
 * 2024-01-12
 * 消息队列Bean配置
 */
@Component
public class MqConfig {

    // ---------------------- param ----------------------

    @Value("${netty.mq.admin.address}")
    private String adminAddress;
    @Value("${netty.mq.accessToken}")
    private String accessToken;


    @Bean
    public NettyMqSpringClientFactory getNettyMqConsumer(){

        NettyMqSpringClientFactory factory = new NettyMqSpringClientFactory();
        factory.setAdminAddress(adminAddress);
        factory.setAccessToken(accessToken);

        return factory;
    }

}