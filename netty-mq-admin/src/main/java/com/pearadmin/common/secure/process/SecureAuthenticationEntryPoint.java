package com.pearadmin.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.pearadmin.common.tools.ServletUtil;
import com.pearadmin.common.web.domain.response.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security 未登录处理 Handler
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class SecureAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result result = Result.failure(401, "未知账户");
        ServletUtil.write(JSON.toJSONString(result));
    }
}
