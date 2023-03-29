package com.pearadmin.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring 上下文
 * <p>
 * 提供便于操作 IOC 的 API
 *
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class BeanContext implements ApplicationContextAware {

    /**
     * Ioc Context
     * */
    public static ApplicationContext applicationContext;

    /**
     * 设置 Spring 上下文
     * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContext.applicationContext = applicationContext;
    }

    /**
     * 获取 Bean 实体
     *
     * @param name 名称
     * */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取 Bean 实例
     *
     * @param name 名称
     * @param clazz 类型
     * */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 获取 Bean 实例
     *
     * @param clazz 类型
     * */
    public static<T> T getBean( Class<T> clazz){ return applicationContext.getBean(clazz); }

    /**
     * Bean 是否存在
     *
     * @param name 名称
     * */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * Bean 是否为单例
     *
     * @param name 名称
     * */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取 Bean 类型
     *
     * @param name 名称
     */
    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

    /**
     * 获取实现了接口，抽象类的bean
     *
     * @param clazz 接口或抽象类
     * @param <T>   接口，抽象类
     * @return 实现了接口，抽象类的bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 获取实现了接口，抽象类的beanName
     *
     * @param clazz 接口，抽象类
     * @return 实现了接口，抽象类的beanName
     */
    public static String[] getBeanNamesForType(Class<?> clazz) {
        return applicationContext.getBeanNamesForType(clazz);
    }

}
