package com.pearadmin.modules.sys.mapper;

import com.pearadmin.modules.sys.domain.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Describe: 字典类型接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysDictMapper {

    /**
     * Describe: 查询字典类型列表
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    List<SysDict> selectList(SysDict sysDict);

    /**
     * Describe: 根据 id 查询字典类型
     * Param: id
     * Return: SysDictType
     */
    SysDict selectById(String id);

    /**
     * Describe: 插入字典类型
     * Param: SysDictType
     * Return: Integer
     */
    Integer insert(SysDict sysDict);

    /**
     * Describe: 根据 Id 修改字典类型
     * Param: SysDictType
     * Return: 执行结果
     */
    Integer updateById(SysDict sysDict);

    /**
     * Describe: 根据 id 删除字典类型
     * Param: id
     * Return: 执行结果
     */
    Integer deleteById(String id);
}
