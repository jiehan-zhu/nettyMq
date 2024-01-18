package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.context.DataContext;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDataSource;
import com.pearadmin.modules.sys.mapper.SysDataSourceMapper;
import com.pearadmin.modules.sys.service.SysDataSourceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
public class SysDataSourceServiceImpl extends ServiceImpl<SysDataSourceMapper, SysDataSource> implements SysDataSourceService {

    @Resource
    private SysDataSourceMapper sysDataSourceMapper;

    @Lazy
    @Resource
    private DataContext dataContext;

    @Override
    public PageInfo<SysDataSource> page(SysDataSource param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDataSource> list = sysDataSourceMapper.selectDataSource(param);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysDataSource entity) {
        dataContext.createDataSource(entity.getName(), entity.getUsername(), entity.getPassword(), entity.getUrl(), entity.getDriver());
        sysDataSourceMapper.insert(entity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysDataSource entity) {
        SysDataSource dataSource = sysDataSourceMapper.selectById(entity.getId());
        sysDataSourceMapper.updateById(entity);
        dataContext.updateDataSource(dataSource.getName(), dataSource.getUsername(), dataSource.getPassword(), dataSource.getUrl(), dataSource.getDriver());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        SysDataSource dataSource = sysDataSourceMapper.selectById(id);
        sysDataSourceMapper.deleteById(id);
        dataContext.removeDataSource(dataSource.getName());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        list.forEach(id -> {
            SysDataSource dataSource = sysDataSourceMapper.selectById((Serializable) id);
            sysDataSourceMapper.deleteById((Serializable) id);
            dataContext.removeDataSource(dataSource.getName());
        });
        return true;
    }
}

