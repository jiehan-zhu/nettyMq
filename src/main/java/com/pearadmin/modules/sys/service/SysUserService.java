package com.pearadmin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDept;
import com.pearadmin.modules.sys.domain.SysRole;
import com.pearadmin.modules.sys.domain.SysUser;

import java.util.List;

/**
 * Describe: 用户服务接口类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * Describe: 根据条件查询用户列表数据  分页
     * Param: username
     * Return: 返回分页用户列表数据
     */
    PageInfo<SysUser> page(SysUser param, PageDomain pageDomain);

    /**
     * Describe: 根据 id 删除用户数据
     * Param: id
     * Return: 操作结果
     */
    boolean remove(String id);

    /**
     * Describe: 根据 id 删除用户数据
     * Param: ids
     * Return: 操作结果
     */
    boolean batchRemove(String[] ids);

    /**
     * Describe: 保存用户角色数据
     * Param: SysUser
     * Return: 操作结果
     */
    boolean saveUserRole(String userId, List<String> roleIds);

    /**
     * Describe: 获取用户角色数据
     * Param: SysUser
     * Return: 操作结果
     */
    List<SysRole> getUserRole(String userId);

    /**
     * 获取用户部门 (数据权限)
     *
     * @param userId 用户编号
     *
     * @return {@link SysDept}
     * */
    List<SysDept> dept(String userId);

    SysUser getUserByUsername(String username);

}

