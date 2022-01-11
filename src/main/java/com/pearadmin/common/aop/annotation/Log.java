package com.pearadmin.common.aop.annotation;

import com.pearadmin.common.aop.enums.BusinessType;

import java.lang.annotation.*;

/**
 * Log 操作日志
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Log {

    /**
     * 默认无参输入
     */
    String value() default "暂无标题";

    /**
     * Title 默认输入
     */
    String title() default "暂无标题";

    /**
     * Describe 默认输入
     */
    String describe() default "暂无介绍";

    /**
     * 业 务 类 型  默认Query
     */
    BusinessType type() default BusinessType.QUERY;
}
