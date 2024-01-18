package com.pearadmin.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.aop.annotation.Log;
import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.sys.domain.SysDataSource;
import com.pearadmin.modules.sys.service.SysDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 多数据源控制器
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@RestController
@Api(tags = {"多数据源"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "dataSource")
public class SysDataSourceController extends BaseController {

    /**
     * 基础路径
     */
    private final String MODULE_PATH = "system/dataSource/";

    @Resource
    private SysDataSourceService sysDataSourceService;

    /**
     * Describe: 数据字典列表视图
     * Param: ModelAndView
     * Return: ModelAndView
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/config/main','sys:config:main')")
    public ModelAndView main() {
        return jumpPage(MODULE_PATH + "main");
    }

    /**
     * 查询多库列表
     *
     * @param sysDataSource 查询参数
     * @return {@link Result}
     */
    @GetMapping("data")
    @Log(title = "查询多库")
    @ApiOperation(value = "查询多库")
    public ResultTable page(PageDomain pageDomain, SysDataSource sysDataSource){
        PageInfo<SysDataSource> pageInfo = sysDataSourceService.page(sysDataSource, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * Describe: 数据字典类型新增视图
     * Param: sysConfig
     * Return: ModelAndView
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/system/config/add','sys:config:add')")
    public ModelAndView add() {
        return jumpPage(MODULE_PATH + "add");
    }


    /**
     * 新增多库
     *
     * @param sysDataSource 多库实体
     */
    @PostMapping("save")
    @Log(title = "新增多库")
    @ApiOperation(value = "新增多库")
    public Result save(@RequestBody SysDataSource sysDataSource){
        return decide(sysDataSourceService.save(sysDataSource));
    }

    /**
     * Describe: 数据字典类型修改视图
     * Param: sysConfig
     * Return: ModelAndView
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/system/config/edit','sys:config:edit')")
    public ModelAndView edit(Model model, String id) {
        model.addAttribute("sysDataSource", sysDataSourceService.getById(id));
        return jumpPage(MODULE_PATH + "edit");
    }

    /**
     * 修改多库
     *
     * @param sysDataSource 多库实体
     */
    @PutMapping("update")
    @Log(title = "修改多库")
    @ApiOperation(value = "修改多库")
    public Result update(@RequestBody SysDataSource sysDataSource){
        return decide(sysDataSourceService.updateById(sysDataSource));
    }

    /**
     * 删除多库
     *
     * @param id 多库编号
     */
    @DeleteMapping("remove/{id}")
    @Log(title = "删除多库")
    @ApiOperation(value = "删除多库")
    public Result remove(@PathVariable("id") String id){
        return decide(sysDataSourceService.removeById(id));
    }

    /**
     * 删除多库
     *
     * @param ids 多库编号
     */
    @DeleteMapping("batchRemove/{ids}")
    @Log(title = "批量删除")
    @ApiOperation(value = "批量删除")
    public Result removeBatch(@PathVariable String ids) {
        return decide(sysDataSourceService.removeByIds(Arrays.asList(ids.split(","))));
    }
}
