package com.pearadmin.common.cache.impl;

import com.pearadmin.common.cache.BaseCache;
import com.pearadmin.modules.sys.domain.SysDict;
import com.pearadmin.modules.sys.domain.SysDictData;
import com.pearadmin.modules.sys.service.SysDictDataService;
import com.pearadmin.modules.sys.service.SysDictService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局字典缓存
 *
 * Author: 就 眠 仪 式
 * CreateTime: 2021/04/21
 * */
@Slf4j
@Component
public class DictionaryCache extends BaseCache<List<SysDictData>> {

    /**
     * @Lazy 注解, 解决 spring boot 2.6 之后禁止循环注入的问题
     *
     * 即 先有鸡 还是 现有蛋
     * */
    @Lazy
    @Resource
    private SysDictService sysDictTypeService;

    @Lazy
    @Resource
    private SysDictDataService sysDictDataService;

    @Override
    public Map<String, List<SysDictData>> load()
    {
        log.info("Refresh Cache - 数据字典");
        Map<String, List<SysDictData>> map = new HashMap<>();
        List<SysDict> dictList = sysDictTypeService.list(null);
        dictList.forEach(dict -> {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setTypeCode(dict.getTypeCode());
            List<SysDictData> dictData = sysDictDataService.list(sysDictData);
            map.put(dict.getTypeCode(),dictData);
        });
        return map;
    }
}