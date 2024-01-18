package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 系统配置接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * Describe: 查询系统配置信息
     * Param: SysConfig
     * Return: 执行结果
     */
    List<SysConfig> selectConfig(SysConfig param);

    /**
     * Describe: 根据 Code 查询系统配置
     * Param: code
     * Return: SysConfig
     */
    SysConfig selectByCode(@Param("code") String code);

}
