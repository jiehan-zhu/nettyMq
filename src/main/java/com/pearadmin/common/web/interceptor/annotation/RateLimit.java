package com.pearadmin.common.web.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RateLimit 限流注解
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 请求次数
     *
     * 默认 100 秒内限制 10 次访问
     * */
    int number() default 10;

    /**
     * 时间限制
     *
     * 默认 100 秒内限制 10 次访问
     * */
    long time() default 100;
}