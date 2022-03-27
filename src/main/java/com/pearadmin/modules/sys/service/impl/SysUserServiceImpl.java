package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDept;
import com.pearadmin.modules.sys.domain.SysRole;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.domain.SysUserRole;
import com.pearadmin.modules.sys.mapper.SysRoleMapper;
import com.pearadmin.modules.sys.mapper.SysUserMapper;
import com.pearadmin.modules.sys.mapper.SysDeptMapper;
import com.pearadmin.modules.sys.mapper.SysUserRoleMapper;
import com.pearadmin.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describe: 用户服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    /**
     * 注入用户服务
     */
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 注入用户角色服务
     */
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 注入角色服务
     */
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * Describe: 根据条件查询用户列表数据
     * Param: username
     * Return: 返回分页用户列表数据
     */
    @Override
    public PageInfo<SysUser> page(SysUser param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysUser> sysUsers = list(new QueryWrapper<>(param));
        return new PageInfo<>(sysUsers);
    }

    /**
     * Describe: 根据 id 删除用户数据
     * Param: id
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(String id) {
        sysUserRoleMapper.deleteByUserId(id);
        sysUserMapper.deleteById(id);
        return true;
    }

    /**
     * Describe: 根据 id 批量删除用户数据
     * Param: ids
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemove(String[] ids) {
        sysUserMapper.deleteBatchIds(Arrays.asList(ids));
        sysUserRoleMapper.deleteByUserIds(ids);
        return true;
    }

    /**
     * Describe: 保存用户角色数据
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    @Transactional
    public boolean saveUserRole(String userId, List<String> roleIds) {
        sysUserRoleMapper.deleteByUserId(userId);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(SequenceUtil.makeStringId());
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        });
        sysUserRoleMapper.batchInsert(sysUserRoles);
        return true;
    }

    /**
     * Describe: 获取
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public List<SysRole> getUserRole(String userId) {
        List<SysRole> allRole = sysRoleMapper.selectList(null);
        List<SysUserRole> myRole = sysUserRoleMapper.selectByUserId(userId);
        allRole.forEach(sysRole -> {
            myRole.forEach(sysUserRole -> {
                if (sysRole.getRoleId().equals(sysUserRole.getRoleId())) {
                    sysRole.setChecked(true);
                }
            });
        });
        return allRole;
    }

    @Override
    public List<SysDept> dept(String userId) {
        return sysDeptMapper.selectDeptByUserId(userId);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        return sysUserMapper.selectOne(wrapper);
    }

}
