package com.pearadmin.common.context;

import com.pearadmin.modules.sys.domain.SysUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Describe: Security 工 具 类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public class UserContext {

    /**
     * 获取当前登录用户的信息，如果未登录返回null
     *
     * @return Object 当前登录用户
     */
    public static SysUser currentUser() {
        return null!=getAuthentication()?(SysUser) getAuthentication().getPrincipal():null;
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return Authentication 权鉴对象
     */
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken)?authentication:null;
    }

    /**
     * 验证当前用户是否登录
     *
     * @return boolean 是否登录
     */
    public static boolean isAuthentication() {
        // if security session eq s-id is not null to index
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    /**
     * SysUser 当前用户
     * */
    public SysUser getPrincipal(){ return (SysUser) getAuthentication().getPrincipal(); }

    /**
     * Username 当前用户名
     * */
    public String getUsername(){ return getPrincipal().getUsername(); }

    /**
     * nickname 当前用户昵称
     * */
    public String getRealName(){ return getPrincipal().getRealName(); }

    /**
     * UserId 用户编号
     * */
    public String getUserId(){ return getPrincipal().getUserId(); }

    /**
     * deptId 当前部门
     * */
    public String getDeptId(){ return getPrincipal().getDeptId(); }
}
