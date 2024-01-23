package com.pearadmin.modules.mq.domain;

import lombok.Data;

/**
 * MqConsumerDto
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Data
public class MqConsumerDto {
    private Integer consumerId;
    private String consumerTopic;
    private String consumerGroup;
    private String consumerThreadUuid;
    private String consumerRegistryKey;
    private String consumerRegistryValue;
    private String consumerClassName;
    private String ip;
    private String port;
    private String consumerStatus;
    private String number;

}
