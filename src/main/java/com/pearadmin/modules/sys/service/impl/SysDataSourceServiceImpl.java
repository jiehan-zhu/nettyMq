package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDataSource;
import com.pearadmin.modules.sys.mapper.SysDataSourceMapper;
import com.pearadmin.modules.sys.service.ISysDataSourceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDataSourceServiceImpl extends ServiceImpl<SysDataSourceMapper, SysDataSource> implements ISysDataSourceService {

    @Resource
    private SysDataSourceMapper sysDataSourceMapper;

    @Override
    public PageInfo<SysDataSource> page(SysDataSource param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDataSource> list = sysDataSourceMapper.selectDataSource(param);
        return new PageInfo<>(list);
    }
}

