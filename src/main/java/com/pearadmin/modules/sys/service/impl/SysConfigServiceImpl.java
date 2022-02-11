package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysConfig;
import com.pearadmin.modules.sys.mapper.SysConfigMapper;
import com.pearadmin.modules.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 系统配置服务接口实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    /**
     * 系统配置数据库操作接口
     */
    @Resource
    private SysConfigMapper sysConfigMapper;

    /**
     * Describe: 根据条件查询系统配置列表数据
     * Param: SysConfig
     * Return: List<SysConfig>
     */
    @Override
    public List<SysConfig> list(SysConfig param) {
        return sysConfigMapper.selectConfig(param);
    }

    /**
     * Describe: 根据条件查询系统配置列表数据 分页
     * Param: SysConfig
     * Return: PageInfo<SysConfig>
     */
    @Override
    public PageInfo<SysConfig> page(SysConfig param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysConfig> list = sysConfigMapper.selectConfig(param);
        return new PageInfo<>(list);
    }

    /**
     * Describe: 根据 Code 查询系统配置
     * Param: code
     * Return: 返回系统配置信息
     */
    @Override
    public SysConfig getByCode(String code) {
        return sysConfigMapper.selectByCode(code);
    }

    @Override
    public String getConfig(String code) {
        SysConfig config = sysConfigMapper.selectByCode(code);
        return config != null ? config.getConfigValue() : "";
    }
}
