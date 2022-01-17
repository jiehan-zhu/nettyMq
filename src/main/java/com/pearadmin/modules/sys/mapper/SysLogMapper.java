package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.common.aop.enums.LoggingType;
import com.pearadmin.modules.sys.domain.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Describe: 日 志 接 口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * Describe: 查询日志信息
     * Param: LoggingType
     * Return: 日志列表
     */
    List<SysLog> selectList(LoggingType loggingType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Describe: 根据 operateName 查询日志
     * Param: operateName
     * Return 日志列表
     */
    List<SysLog> selectTopLoginLog(String operateName);

}
