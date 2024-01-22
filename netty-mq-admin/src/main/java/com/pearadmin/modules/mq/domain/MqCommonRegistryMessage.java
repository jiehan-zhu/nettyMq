package com.pearadmin.modules.mq.domain;

import lombok.Data;

import java.util.Date;

/**
 * registryMessage
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Data
public class MqCommonRegistryMessage {

    private int id;
    private String data;
    private Date addTime;


}
