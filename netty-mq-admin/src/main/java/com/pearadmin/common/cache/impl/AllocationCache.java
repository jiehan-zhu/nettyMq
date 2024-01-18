package com.pearadmin.common.cache.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.pearadmin.common.cache.BaseCache;
import com.pearadmin.modules.sys.domain.SysConfig;
import com.pearadmin.modules.sys.service.SysConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局配置缓存
 *
 * Author: 就 眠 仪 式
 * CreateTime: 2021/04/21
 * */
@Slf4j
@Component
public class AllocationCache extends BaseCache<String> {

    @Resource
    private SysConfigService sysConfigService;

    @Override
    public Map<String, String> load() {
        log.info("Refresh Cache - 全局配置");
        Map<String, String> map = new HashMap<>();
        List<SysConfig> list = sysConfigService.list(new QueryWrapper<>());
        if(list.size() > 0) {
            for (SysConfig sysConfig : list) {
                map.put(sysConfig.getConfigCode(), sysConfig.getConfigValue());
            }
        }
        return map;
    }
}