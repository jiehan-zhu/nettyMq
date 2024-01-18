package com.pearadmin.common.context;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pearadmin.modules.sys.domain.SysLog;
import com.pearadmin.modules.sys.domain.SysRole;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.mapper.SysPowerMapper;
import com.pearadmin.modules.sys.mapper.SysRoleMapper;
import com.pearadmin.modules.sys.mapper.SysUserMapper;
import com.pearadmin.modules.sys.service.SysLogService;
import com.pearadmin.modules.sys.service.SysUserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 系统基础 API
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class BaseContext {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysLogService sysLogService;

    @Resource
    private SysPowerMapper sysPowerMapper;

    @Resource
    private SysUserService sysUserService;

    public SysUser getUserByName(String username) {
        SysUser sysUser = sysUserService.getUserByUsername(username);
        if (sysUser != null) {
            sysUser.setPowerList(sysPowerMapper.selectByUsername(username));
        }
        return sysUser;
    }

    public SysUser getUserById(String id) {
        return sysUserMapper.selectById(id);
    }

    public List<SysRole> getRolesByUsername(String username) {
        return sysRoleMapper.selectByUsername(username);
    }

    @Async
    public Boolean saveLog(SysLog log) {
        return sysLogService.save(log);
    }
}
