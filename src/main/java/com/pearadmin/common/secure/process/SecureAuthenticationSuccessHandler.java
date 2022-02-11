package com.pearadmin.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.pearadmin.common.aop.enums.BusinessType;
import com.pearadmin.common.aop.enums.LoggingType;
import com.pearadmin.common.context.UserContext;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.tools.ServletUtil;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.modules.sys.domain.SysLog;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.service.SysLogService;
import com.pearadmin.modules.sys.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class SecureAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private SysLogService sysLogService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        SysLog sysLog = new SysLog();
        sysLog.setId(SequenceUtil.makeStringId());
        sysLog.setTitle("登录");
        sysLog.setDescription("登录成功");
        sysLog.setBusinessType(BusinessType.OTHER);
        sysLog.setSuccess(true);
        sysLog.setLoggingType(LoggingType.LOGIN);
        sysLogService.save(sysLog);

        SysUser sysUser = new SysUser();
        sysUser.setUserId(UserContext.currentUser().getUserId());
        sysUser.setLastTime(LocalDateTime.now());
        sysUserService.updateById(sysUser);

        SysUser currentUser = (SysUser) authentication.getPrincipal();
        currentUser.setLastTime(LocalDateTime.now());
        request.getSession().setAttribute("currentUser", authentication.getPrincipal());
        Result result = Result.success("登录成功");
        ServletUtil.write(JSON.toJSONString(result));
    }
}
