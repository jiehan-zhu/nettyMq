package com.pearadmin.system.service;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.system.domain.SysDictData;

import java.util.List;

/**
 * Describe: 字典值服务类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public interface ISysDictDataService {

    /**
     * Describe: 根据条件查询字典类型列表数据
     * Param: SysDictData
     * Return: List<SysDictType>
     */
    List<SysDictData> list(SysDictData sysDictData);

    /**
     * 根据字典code获取可用的字典列表数据
     *
     * @param typeCode
     * @return
     */
    List<SysDictData> selectByCode(String typeCode);

    /**
     * 刷新字典缓存
     *
     * @param typeCode
     */
    void refreshCacheTypeCode(String typeCode);

    /**
     * Describe: 根据条件查询字典类型列表数据 分页
     * Param: SysDictData
     * Return: PageInfo<SysDictType>
     */
    PageInfo<SysDictData> page(SysDictData sysDictData, PageDomain pageDomain);

    /**
     * Describe: 插入 SysDictData 数据
     * Param: SysDictData
     * Return: Boolean
     */
    Boolean save(SysDictData sysDictData);

    /**
     * Describe: 根据 Id 查询字典类型
     * Param: SysDictData
     * Return: List<SysDictData>
     */
    SysDictData getById(String id);

    /**
     * Describe: 删除 SysDictData 数据
     * Param: SysDictData
     * Return: Boolean
     */
    Boolean remove(String id);

    /**
     * Describe: 修改 SysDictData 数据
     * Param: SysDictData
     * Return: Boolean
     */
    Boolean updateById(SysDictData sysDictData);


    /**
     * 查询表字典通过查询指定table的 text code key 获取字典值
     *
     * @param table 表名
     * @param text  label
     * @param code  value
     * @return
     */
    List<SysDictData> queryTableDictItemsByCode(String table, String text, String code);


    /**
     * 查询表字典 通过查询指定table的 text code 获取字典（指定查询条件）
     *
     * @param table 表名
     * @param text  label
     * @param code  value
     * @param filterSql 条件
     */
    List<SysDictData> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);


    /**
     * 查询表字典 通过查询指定table的 text code key 获取字典值，包含value
     *
     * @param table    表名
     * @param text     label
     * @param code     value
     * @param keyArray values
     * @return
     */
    List<SysDictData> queryTableDictByKeys(String table, String text, String code, String[] keyArray);

}
