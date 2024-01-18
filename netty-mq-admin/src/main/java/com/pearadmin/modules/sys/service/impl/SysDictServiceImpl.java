package com.pearadmin.modules.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDict;
import com.pearadmin.modules.sys.mapper.SysDictDataMapper;
import com.pearadmin.modules.sys.mapper.SysDictMapper;
import com.pearadmin.modules.sys.service.SysDictDataService;
import com.pearadmin.modules.sys.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 字典类型服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Resource
    private SysDictDataService sysDictDataService;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    /**
     * Describe: 根据条件查询用户列表数据
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    @Override
    public List<SysDict> list(SysDict sysDict) {
        return sysDictMapper.selectList(sysDict);
    }

    /**
     * Describe: 根据条件查询用户列表数据 分页
     * Param: SysDictType
     * Return: PageInfo<SysDictType>
     */
    @Override
    public PageInfo<SysDict> page(SysDict sysDict, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDict> list = sysDictMapper.selectList(sysDict);
        return new PageInfo<>(list);
    }

    /**
     * Describe: 保存字典数据
     * Param: SysDictType
     * Return: Boolean
     */
    @Override
    public Boolean save(SysDict sysDict) {
        Integer result = sysDictMapper.insert(sysDict);
        if (result > 0) {
            sysDictDataService.refreshCacheTypeCode(sysDict.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Describe: 根据 ID 查询字典类型
     * Param: id
     * Return: 返回字典类型信息
     */
    @Override
    public SysDict getById(String id) {
        return sysDictMapper.selectById(id);
    }

    /**
     * Describe: 根据 ID 修改字典类型
     * Param: SysDictType
     * Return: Boolean
     */
    @Override
    public Boolean updateById(SysDict sysDict) {
        int result = sysDictMapper.updateById(sysDict);
        SysDict dictType = sysDictMapper.selectById(sysDict.getId());
        if (result > 0) {
            sysDictDataService.refreshCacheTypeCode(dictType.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Describe: 根据 ID 删除字典类型
     * Param: id
     * Return: Boolean
     */
    @Override
    public Boolean remove(String id) {
        SysDict sysDict = sysDictMapper.selectById(id);
        if (sysDict != null) {
            sysDictMapper.deleteById(id);
            sysDictDataMapper.deleteByCode(sysDict.getTypeCode());
            sysDictDataService.refreshCacheTypeCode(sysDict.getTypeCode());
        }

        return true;
    }
}
