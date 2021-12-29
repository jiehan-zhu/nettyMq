package com.pearadmin.system.service;

import com.pearadmin.system.domain.SysLog;
import com.pearadmin.system.domain.SysRole;
import com.pearadmin.system.domain.SysUser;

import java.util.List;

/**
 * Describe: 系统基础 API
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public interface SystemService {

    /**
     * 根据用户账号查询用户信息
     *
     * @param username
     * @return
     */
    SysUser getUserByName(String username);


    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    SysUser getUserById(String id);

    /**
     * 通过用户账号查询角色集合
     *
     * @param username
     * @return
     */
    List<SysRole> getRolesByUsername(String username);



    /**
     * 存储日志
     *
     * @param log 日志对象
     */
    Boolean saveLog(SysLog log);
}
