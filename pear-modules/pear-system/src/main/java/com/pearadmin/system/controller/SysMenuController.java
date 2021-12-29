package com.pearadmin.system.controller;

import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysMenu;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miko
 * @version V1.0
 * @date 2021/12/29 14:45
 * @Description:
 */
@RestController
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "menu/")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * Describe: 根据 username 获取菜单数据
     * Param SysRole
     * Return 执行结果
     */
    @GetMapping("/data")
    @ApiOperation(value = "获取用户菜单数据")
    public List<SysMenu> getUserMenu() {
        SysUser sysUser = SecurityUtil.currentUser();
        List<SysMenu> menus = sysMenuService.getUserMenu(sysUser.getUsername());
        return sysMenuService.toUserMenu(menus, "0");
    }
}
