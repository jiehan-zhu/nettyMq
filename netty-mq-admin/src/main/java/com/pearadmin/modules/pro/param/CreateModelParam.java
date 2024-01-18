package com.pearadmin.modules.pro.param;

import lombok.Data;

/**
 * Describe: 模型创建实体
 * Author: 就眠仪式
 * createTime: 2021/09/09
 */
@Data
public class CreateModelParam {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板标识
     */
    private String key;

    /**
     * 模板标识
     */
    private String description;

    /**
     * 模板版本
     */
    private String version;

}
