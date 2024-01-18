package com.pearadmin.common.secure.domain;

import com.pearadmin.modules.sys.domain.SysPower;
import com.pearadmin.modules.sys.domain.SysRole;
import com.pearadmin.modules.sys.domain.SysUser;
import com.pearadmin.modules.sys.mapper.SysPowerMapper;
import com.pearadmin.modules.sys.mapper.SysRoleMapper;
import com.pearadmin.modules.sys.mapper.SysUserMapper;
import com.pearadmin.modules.sys.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Security 加载用户信息服务
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class SecureUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPowerMapper sysPowerMapper;

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("Account Not Found");
        }
        List<SysPower> powerList = sysPowerMapper.selectByUsername(username);
        List<SysRole> roleList = sysRoleMapper.selectByUsername(username);
        sysUser.setPowerList(powerList);
        sysUser.setRoles(roleList);
        return sysUser;
    }

}
