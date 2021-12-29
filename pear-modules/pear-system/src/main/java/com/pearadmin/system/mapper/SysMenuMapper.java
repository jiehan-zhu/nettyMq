package com.pearadmin.system.mapper;

import com.pearadmin.system.domain.SysMenu;
import com.pearadmin.system.domain.SysPower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 系统权限接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysMenuMapper {


    /**
     * Describe: 根据 username 查询用户菜单
     * Param: username
     * Return: ResuMenu
     */
    List<SysMenu> selectMenuByUsername(String username);

}
