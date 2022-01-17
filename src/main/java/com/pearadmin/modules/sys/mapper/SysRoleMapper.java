package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 系统角色接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * Describe: 根据 username 查询用户权限
     * Param: username
     * Return: SysPower
     */
    List<SysRole> selectByUsername(String username);

    /**
     * Describe: 查询角色列表
     * Param: SysRole
     * Return: List<SysRole>
     */
    List<SysRole> selectRole(SysRole param);

}
