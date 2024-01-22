package com.pearadmin.modules.mq.domain;

import lombok.Data;

import java.util.Date;

/**
 * registryData
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Data
public class MqCommonRegistryData {

    private int id;
    private String key;         // 注册Key
    private String value;       // 注册Value
    private Date updateTime;    // 更新时间
    private String status;      //注册状态(RUNNING/STOP) 新建的消费者的状态默认都是 RUNNING


}
