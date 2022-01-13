package com.pearadmin.common.secure.support;

import com.alibaba.fastjson.JSON;
import com.pearadmin.common.tools.ServletUtil;
import com.pearadmin.common.tools.string.StringUtil;
import com.pearadmin.common.web.domain.response.Result;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security 权限注解实现
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class SecureCaptchaSupport extends OncePerRequestFilter implements Filter {

    /**
     * 需要过滤的接口条件
     * */
    private String defaultFilterProcessUrl = "/login";
    private String method = "POST";

    /**
     * 验证码校检逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (method.equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            String captcha = ServletUtil.getRequest().getParameter("captcha");
            response.setContentType("application/json;charset=UTF-8");
            if (StringUtil.isEmpty(captcha)) {
                response.getWriter().write(JSON.toJSONString(Result.failure("验证码不能为空!")));
                return;
            }
            if (!CaptchaUtil.ver(ServletUtil.getRequest().getParameter("captcha"), ServletUtil.getRequest())) {
                response.getWriter().write(JSON.toJSONString(Result.failure("验证码错误!")));
                return;
            }
        }
        chain.doFilter(request, response);
    }
}