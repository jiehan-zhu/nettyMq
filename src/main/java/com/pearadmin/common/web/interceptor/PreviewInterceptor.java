package com.pearadmin.common.web.interceptor;

import com.pearadmin.common.tools.ServletUtil;
import com.pearadmin.common.web.exception.base.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Preview 预览拦截器
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class PreviewInterceptor implements HandlerInterceptor{

    private static Boolean enablePreview = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(enablePreview) {
            if(!ServletUtil.getMethod().equals("GET")){
                throw  new BusinessException("预览环境,禁止操作");
            }
        }
        return true;
    }
}
