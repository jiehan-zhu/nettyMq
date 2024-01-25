package com.pearadmin.common.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义拦截器
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermessionLimit {
	
	/**
	 * 登陆拦截 (默认拦截)
	 */
	boolean limit() default true;

}