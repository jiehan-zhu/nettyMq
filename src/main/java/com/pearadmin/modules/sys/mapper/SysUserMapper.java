package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 用户接口层
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {



    /**
     * Describe: 重置部门
     * Param: deptId
     * Return: Integer
     */
    Integer resetDeptByDeptId(String deptId);

    /**
     * Describe: 批量重置部门
     * Param: deptIds
     * Return: Integer
     */
    Integer resetDeptByDeptIds(String[] deptIds);

}
