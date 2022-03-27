package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.cache.impl.DictionaryCache;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDictData;
import com.pearadmin.modules.sys.mapper.SysDictDataMapper;
import com.pearadmin.modules.sys.service.SysDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 数据字典实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Resource
    private DictionaryCache dictionaryCache;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictData> list(SysDictData sysDictData) {
        return sysDictDataMapper.selectList(new QueryWrapper<>(sysDictData));
    }

    @Override
    public List<SysDictData> selectByCode(String typeCode) {
        return dictionaryCache.get(typeCode);
    }

    @Override
    public void refreshCacheTypeCode(String typeCode) {
        dictionaryCache.reload();
    }

    @Override
    public PageInfo<SysDictData> page(SysDictData sysDictData, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDictData> list = sysDictDataMapper.selectList(new QueryWrapper<>(sysDictData));
        return new PageInfo<>(list);
    }

    @Override
    public Boolean save(SysDictData sysDictData) {
        Integer result = sysDictDataMapper.insert(sysDictData);
        if (result > 0) {
            refreshCacheTypeCode(sysDictData.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SysDictData getById(String id) {
        return sysDictDataMapper.selectById(id);
    }

    @Override
    public Boolean updateById(SysDictData sysDictData) {
        int result = sysDictDataMapper.updateById(sysDictData);
        if (result > 0) {
            refreshCacheTypeCode(sysDictData.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean remove(String id) {
        SysDictData sysDictData = sysDictDataMapper.selectById(id);
        if (sysDictData != null) {
            sysDictDataMapper.deleteById(id);
            refreshCacheTypeCode(sysDictData.getTypeCode());
        }
        return true;
    }

    @Override
    public List<SysDictData> queryTableDictItemsByCode(String table, String text, String code) {
        return sysDictDataMapper.queryTableDictItemsByCode(table, text, code);
    }

    @Override
    public List<SysDictData> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
        return sysDictDataMapper.queryTableDictItemsByCodeAndFilter(table, text, code, filterSql);
    }

    @Override
    public List<SysDictData> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
        return sysDictDataMapper.queryTableDictByKeys(table, text, code, keyArray);
    }
}
