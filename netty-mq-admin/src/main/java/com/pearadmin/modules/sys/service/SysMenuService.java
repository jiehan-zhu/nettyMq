package com.pearadmin.modules.sys.service;

import com.pearadmin.modules.sys.domain.SysMenu;

import java.util.List;

/**
 * @author miko
 * @version V1.0
 * @date 2021/12/29 14:43
 * @Description:
 */
public interface SysMenuService {
    /**
     * Describe: 获取用户菜单数据
     * Param: SysUser
     * Return: 操作结果
     */
    List<SysMenu> getUserMenu(String username);

    /**
     * Describe: 递归获取菜单tree
     * Param: sysMenus
     * Return: 操作结果
     */
    List<SysMenu> toUserMenu(List<SysMenu> sysMenus, String parentId);
}
