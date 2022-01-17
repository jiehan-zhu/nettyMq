package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Describe: 字典类型接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * Describe: 查询字典类型列表
     * Param: SysDictType
     * Return: List<SysDictType>
     */
    List<SysDict> selectList(SysDict sysDict);
}
