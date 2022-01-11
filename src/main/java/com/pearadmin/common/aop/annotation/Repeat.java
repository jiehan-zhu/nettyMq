package com.pearadmin.common.aop.annotation;

import java.lang.annotation.*;

/**
 * Repeat 防止重复提交
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repeat {

    // @annotation

}