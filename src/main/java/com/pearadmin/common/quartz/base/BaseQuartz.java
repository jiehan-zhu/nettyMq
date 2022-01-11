package com.pearadmin.common.quartz.base;

/**
 * Quartz Job 的实现接口
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
public interface BaseQuartz {

    /**
     * 任 务 实 现
     */
    void run(String params) throws Exception;
}
