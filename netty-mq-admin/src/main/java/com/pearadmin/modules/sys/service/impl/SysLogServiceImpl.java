package com.pearadmin.modules.sys.service.impl;

import com.pearadmin.common.aop.enums.LoggingType;
import com.pearadmin.common.aop.enums.RequestModel;
import com.pearadmin.common.context.UserContext;
import com.pearadmin.common.tools.ServletUtil;
import com.pearadmin.modules.sys.domain.SysLog;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.mapper.SysLogMapper;
import com.pearadmin.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Describe: 日 志 服 务 接 口 实 现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public boolean save(SysLog sysLog) {
        sysLog.setOperateAddress(ServletUtil.getRemoteHost());
        sysLog.setMethod(ServletUtil.getRequestURI());
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setRequestMethod(RequestModel.valueOf(ServletUtil.getMethod()));
        sysLog.setOperateUrl(ServletUtil.getRequestURI());
        sysLog.setBrowser(ServletUtil.getBrowser());
        sysLog.setRequestBody(ServletUtil.getQueryParam());
        sysLog.setSystemOs(ServletUtil.getSystem());
        SysUser currentUser = UserContext.currentUser();
        sysLog.setOperateName(null != currentUser ? currentUser.getUsername() : "未登录用户");
        int result = sysLogMapper.insert(sysLog);
        return result > 0;
    }

    @Override
    public List<SysLog> data(LoggingType loggingType, LocalDateTime startTime, LocalDateTime endTime) {
        return sysLogMapper.selectList(loggingType, startTime, endTime);
    }

    @Override
    public SysLog getById(String id) {
        return sysLogMapper.selectById(id);
    }

    @Override
    public List<SysLog> selectTopLoginLog(String operateName) {
        return sysLogMapper.selectTopLoginLog(operateName);
    }

}
