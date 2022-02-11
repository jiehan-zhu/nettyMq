package com.pearadmin.modules.sys.service;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDict;

import java.util.List;

/**
 * Describe: 数据字典类型服务类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public interface SysDictService {

    /**
     * Describe: 根据条件查询字典类型列表数据
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    List<SysDict> list(SysDict sysDict);

    /**
     * Describe: 根据条件查询字典类型列表数据 分页
     * Param: SysDictType
     * Return: PageInfo<SysDictType>
     */
    PageInfo<SysDict> page(SysDict sysDict, PageDomain pageDomain);

    /**
     * Describe: 根据 Id 查询字典类型
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    SysDict getById(String id);

    /**
     * Describe: 插入 SysDictType 数据
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    Boolean save(SysDict sysDict);

    /**
     * Describe: 修改 SysDictType 数据
     * Param: SysDictType
     * Return: Boolean
     */
    Boolean updateById(SysDict sysDict);

    /**
     * Describe: 删除 SysDictType 数据
     * Param: SysDictType
     * Return: Boolean
     */
    Boolean remove(String id);
}
