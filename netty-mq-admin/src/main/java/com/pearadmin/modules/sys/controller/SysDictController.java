package com.pearadmin.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.sys.domain.SysDict;
import com.pearadmin.modules.sys.service.SysDictService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 数据字典控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@Api(tags = {"数据字典"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "dictType")
public class SysDictController extends BaseController {

    private final String MODULE_PATH = "system/dict/";

    @Resource
    private SysDictService sysDictTypeService;

    /**
     * Describe: 数据字典列表视图
     * Param: ModelAndView
     * Return: ModelAndView
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/dictType/main','sys:dictType:main')")
    public ModelAndView main() {
        return jumpPage(MODULE_PATH + "main");
    }

    /**
     * Describe: 数据字典列表数据
     * Param: sysDictType
     * Return: ResuTable
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/dictType/data','sys:dictType:data')")
    public ResultTable data(SysDict sysDict, PageDomain pageDomain) {
        PageInfo<SysDict> pageInfo = sysDictTypeService.page(sysDict, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    @GetMapping("list")
    @PreAuthorize("hasPermission('/system/dictType/data','sys:dictType:data')")
    public ResultTable list(SysDict sysDict, PageDomain pageDomain) {
        List<SysDict> list = sysDictTypeService.list(sysDict);
        return dataTable(list);
    }

    /**
     * Describe: 数据字典类型新增视图
     * Param: sysDictType
     * Return: ModelAndView
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/system/dictType/add','sys:dictType:add')")
    public ModelAndView add() {
        return jumpPage(MODULE_PATH + "add");
    }

    /**
     * Describe: 新增字典类型接口
     * Param: sysDictType
     * Return: ResuBean
     */
    @PostMapping("save")
    @PreAuthorize("hasPermission('/system/dictType/add','sys:dictType:add')")
    public Result save(@RequestBody SysDict sysDict) {
        sysDict.setId(SequenceUtil.makeStringId());
        boolean result = sysDictTypeService.save(sysDict);
        return decide(result);
    }

    /**
     * Describe: 数据字典类型修改视图
     * Param: sysDictType
     * Return: ModelAndView
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/system/dictType/edit','sys:dictType:edit')")
    public ModelAndView edit(Model model, String dictTypeId) {
        model.addAttribute("sysDict", sysDictTypeService.getById(dictTypeId));
        return jumpPage(MODULE_PATH + "edit");
    }

    /**
     * Describe: 数据字典类型修改视图
     * Param: sysDictType
     * Return: ModelAndView
     */
    @PutMapping("update")
    @PreAuthorize("hasPermission('/system/dictType/edit','sys:dictType:edit')")
    public Result update(@RequestBody SysDict sysDict) {
        boolean result = sysDictTypeService.updateById(sysDict);
        return decide(result);
    }

    /**
     * Describe: 数据字典删除
     * Param: sysDictType
     * Return: ModelAndView
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/system/dictType/remove','sys:dictType:remove')")
    public Result remove(@PathVariable("id") String id) {
        Boolean result = sysDictTypeService.remove(id);
        return decide(result);
    }

    /**
     * Describe: 根据 Id 启用字典
     * Param dictId
     * Return ResuTree
     */
    @PutMapping("enable")
    public Result enable(@RequestBody SysDict sysDict) {
        sysDict.setEnable("0");
        boolean result = sysDictTypeService.updateById(sysDict);
        return decide(result);
    }

    /**
     * Describe: 根据 Id 禁用字典
     * Param dictId
     * Return ResuTree
     */
    @PutMapping("disable")
    public Result disable(@RequestBody SysDict sysDict) {
        sysDict.setEnable("1");
        boolean result = sysDictTypeService.updateById(sysDict);
        return decide(result);
    }
}
