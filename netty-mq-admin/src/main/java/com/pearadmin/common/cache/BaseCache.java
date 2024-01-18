package com.pearadmin.common.cache;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Cache 缓存容器
 * <p>
 * 基于 HashMap 做容器, Spring Scheduled 提供定时刷新机制, 通过 extends 集成实现 load 接口
 * 提供 Get Reload 开发 Api
 *
 * @serial 2.0.0
 * @author 就眠儀式
 */
@EnableScheduling
public abstract class BaseCache<T> {

    /**
     * 容器
     * */
    public Map<String,T> cache = new HashMap<>();

    /**
     * 获取
     * */
    public T get(String key)
    {
        return cache.get(key);
    }

    /**
     * 刷新
     * */
    public void reload()
    {
        cache = load();
    }

    /**
     * 加载
     * */
    public abstract Map<String,T> load();

    /**
     * 刷新
     * */
    @Scheduled(fixedDelay = 30000)
    public void time()
    {
        cache = load();
    }
}