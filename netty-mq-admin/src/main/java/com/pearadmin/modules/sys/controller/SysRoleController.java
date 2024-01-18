package com.pearadmin.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.common.web.domain.response.module.ResultTree;
import com.pearadmin.common.web.interceptor.enums.Scope;
import com.pearadmin.modules.sys.domain.SysRole;
import com.pearadmin.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Describe: 角 色 控 制 器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@Api(tags = {"系统角色"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "role")
public class SysRoleController extends BaseController {

    /**
     * Describe: 基础路径
     */
    private static String MODULE_PATH = "system/role/";

    /**
     * Describe: 角色模块服务
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * Describe: 获取角色列表视图
     * Param ModelAndView
     * Return 用户列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取角色列表视图")
    @PreAuthorize("hasPermission('/system/role/main','sys:role:main')")
    public ModelAndView main() {
        return jumpPage(MODULE_PATH + "main");
    }

    /**
     * Describe: 获取角色列表数据
     * Param SysRole PageDomain
     * Return 角色列表数据
     */
    @GetMapping("data")
    @ApiOperation(value = "获取角色列表数据")
    @PreAuthorize("hasPermission('/system/role/data','sys:role:data')")
    public ResultTable data(PageDomain pageDomain, SysRole param) {
        PageInfo<SysRole> pageInfo = sysRoleService.page(param, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * Describe: 获取角色新增视图
     * Param ModelAndView
     * Return 角色新增视图
     */
    @GetMapping("add")
    @ApiOperation(value = "获取角色新增视图")
    @PreAuthorize("hasPermission('/system/role/add','sys:role:add')")
    public ModelAndView add() {
        return jumpPage(MODULE_PATH + "add");
    }

    /**
     * Describe: 保存角色信息
     * Param SysRole
     * Return 执行结果
     */
    @PostMapping("save")
    @ApiOperation(value = "保存角色数据")
    @PreAuthorize("hasPermission('/system/role/add','sys:role:add')")
    public Result save(@RequestBody SysRole sysRole) {
        sysRole.setDataScope(Scope.ALL);
        return decide(sysRoleService.save(sysRole));
    }

    /**
     * Describe: 获取角色修改视图
     * Param ModelAndView
     * Return 角色修改视图
     */
    @GetMapping("edit")
    @ApiOperation(value = "获取角色修改视图")
    @PreAuthorize("hasPermission('/system/role/edit','sys:role:edit')")
    public ModelAndView edit(ModelAndView modelAndView, String roleId) {
        modelAndView.addObject("sysRole", sysRoleService.getById(roleId));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }

    /**
     * Describe: 修改角色信息
     * Param SysRole
     * Return 执行结果
     */
    @PutMapping("update")
    @ApiOperation(value = "修改角色数据")
    @PreAuthorize("hasPermission('/system/role/edit','sys:role:edit')")
    public Result update(@RequestBody SysRole sysRole) {
        return decide(sysRoleService.update(sysRole));
    }

    /**
     * Describe: 获取角色授权视图
     * Param ModelAndView
     * Return ModelAndView
     */
    @GetMapping("power")
    @ApiOperation(value = "获取分配角色权限视图")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    public ModelAndView power(Model model, String roleId) {
        model.addAttribute("roleId", roleId);
        return jumpPage(MODULE_PATH + "power");
    }

    /**
     * Describe: 获取数据授权视图
     * Param ModelAndView
     * Return ModelAndView
     */
    @GetMapping("dept")
    @ApiOperation(value = "获取分配数据权限视图")
    @PreAuthorize("hasPermission('/system/role/dept','sys:role:dept')")
    public ModelAndView dept(Model model, String roleId) {
        model.addAttribute("sysRole", sysRoleService.getById(roleId));
        model.addAttribute("roleId", roleId);
        return jumpPage(MODULE_PATH + "dept");
    }

    /**
     * Describe: 保存操作权限
     * Param RoleId PowerIds
     * Return Result
     */
    @PutMapping("saveRolePower")
    @ApiOperation(value = "保存角色权限数据")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    public Result saveRolePower(String roleId, String powerIds) {
        return decide(sysRoleService.saveRolePower(roleId, Arrays.asList(powerIds.split(","))));
    }

    /**
     * Describe: 保存数据权限
     * Param RoleId PowerIds
     * Return Result
     */
    @PutMapping("saveRoleDept")
    @ApiOperation(value = "保存角色部门数据")
    public Result saveRoleDept(String roleId, Scope dataScope, String deptIds) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setDataScope(dataScope);
        boolean result1 = sysRoleService.update(sysRole);
        boolean result2 = sysRoleService.saveRoleDept(roleId, deptIds==null ? new ArrayList<>() : Arrays.asList(deptIds.split(",")));
        return decide(result1 && result2);
    }

    /**
     * Describe: 获取角色权限
     * Param RoleId
     * Return ResultTree
     */
    @GetMapping("getRolePower")
    @ApiOperation(value = "获取角色权限数据")
    @PreAuthorize("hasPermission('/system/role/power','sys:role:power')")
    public ResultTree getRolePower(String roleId) {
        return dataTree(sysRoleService.getRolePower(roleId));
    }

    /**
     * Describe: 获取角色权限
     * Param RoleId
     * Return ResultTree
     */
    @GetMapping("getRoleDept")
    @ApiOperation(value = "获取角色权限数据")
    @PreAuthorize("hasPermission('/system/role/dept','sys:role:dept')")
    public ResultTree getRoleDept(String roleId) {
        return dataTree(sysRoleService.getRoleDept(roleId));
    }


    /**
     * Describe: 用户删除接口
     * Param: id
     * Return: Result
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除角色数据")
    @PreAuthorize("hasPermission('/system/role/remove','sys:role:remove')")
    public Result remove(@PathVariable String id) {
        return decide(sysRoleService.remove(id));
    }

    /**
     * Describe: 用户批量删除接口
     * Param: ids
     * Return: Result
     */
    @DeleteMapping("batchRemove/{ids}")
    @ApiOperation(value = "批量删除角色数据")
    @PreAuthorize("hasPermission('/system/role/remove','sys:role:remove')")
    public Result batchRemove(@PathVariable String ids) {
        return decide(sysRoleService.batchRemove(ids.split(",")));
    }

    /**
     * Describe: 根据 Id 启用角色
     * Param: roleId
     * Return: Result
     */
    @PutMapping("enable")
    @ApiOperation(value = "启用角色")
    public Result enable(@RequestBody SysRole sysRole) {
        sysRole.setEnable("1");
        return decide(sysRoleService.update(sysRole));
    }

    /**
     * Describe: 禁用角色
     * Param: roleId
     * Return: Result
     */
    @PutMapping("disable")
    @ApiOperation(value = "禁用角色")
    public Result disable(@RequestBody SysRole sysRole) {
        sysRole.setEnable("0");
        return decide(sysRoleService.update(sysRole));
    }

}
