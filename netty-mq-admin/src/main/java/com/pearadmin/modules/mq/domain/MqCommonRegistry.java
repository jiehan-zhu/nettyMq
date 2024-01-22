package com.pearadmin.modules.mq.domain;

import lombok.Data;

import java.util.List;

/**
 * registry
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Data
public class MqCommonRegistry {

    private int id;
    private String key;         // 注册Key
    private String data;        // 注册Value有效数据

    // plugin
    private List<String> dataList;


}
