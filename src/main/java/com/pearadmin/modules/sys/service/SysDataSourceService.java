package com.pearadmin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDataSource;
import com.pearadmin.modules.sys.domain.SysDept;

public interface SysDataSourceService extends IService<SysDataSource> {

    /**
     * Describe: 分页查询部门数据
     * Param: queryRoleParam
     * Param: pageDomain
     * Return: 操作结果
     */
    PageInfo<SysDataSource> page(SysDataSource param, PageDomain pageDomain);
}
