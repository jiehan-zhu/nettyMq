package com.pearadmin.system.service.impl;

import com.pearadmin.common.config.proprety.SecurityProperty;
import com.pearadmin.system.domain.SysMenu;
import com.pearadmin.system.mapper.SysMenuMapper;
import com.pearadmin.system.service.ISysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miko
 * @version V1.0
 * @date 2021/12/29 14:43
 * @Description:
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;
    /**
     * 超级管理员配置
     */
    @Resource
    private SecurityProperty securityProperty;
    /**
     * Describe: 获取用户菜单
     * Param: username
     * Return: Result
     */
    @Override
    public List<SysMenu> getUserMenu(String username) {
        String name = !(securityProperty.isSuperAuthOpen() && username.equals(securityProperty.getSuperAdmin())) ? username : "";
        return sysMenuMapper.selectMenuByUsername(name);
    }

    /**
     * Describe: 递归获取菜单tree
     * Param: sysMenus
     * Return: 操作结果
     */
    @Override
    public List<SysMenu> toUserMenu(List<SysMenu> sysMenus, String parentId) {
        List<SysMenu> list = new ArrayList<>();
        for (SysMenu menu : sysMenus) {
            if (parentId.equals(menu.getParentId())) {
                menu.setChildren(toUserMenu(sysMenus, menu.getId()));
                list.add(menu);
            }
        }
        return list;
    }
}
