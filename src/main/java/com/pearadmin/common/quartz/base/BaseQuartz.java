package com.pearadmin.common.quartz.base;

/**
 * Describe: 定时任务服务接口
 * Author: 就眠仪式
 * CreateTime: 2019/10/23
 */
public interface BaseQuartz {

    /**
     * 任 务 实 现
     */
    void run(String params) throws Exception;
}
