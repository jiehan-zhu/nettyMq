package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pearadmin.common.web.base.BaseDomain;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Describe: 用户领域模型
 * @author 就 眠 仪 式
 * @date 2019/10/23
 */
@Data
@Alias("SysUser")
@TableName("sys_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUser extends BaseDomain implements UserDetails, CredentialsContainer {

    /**
     * 编号
     */
    @TableId
    private String userId;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态
     */
    private String status;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 所属部门
     */
    private String deptId;

    /**
     * 是否启用
     */
    private String enable;

    /**
     * 是否登录
     */
    private String login;

    /**
     * 上次登录
     */
    private LocalDateTime lastTime;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private String roleIds;

    /**
     * 角色列表
     * */
    @TableField(exist = false)
    private List<SysRole> roles;


    /**
     * 权限列表
     */
    @TableField(exist = false)
    private List<SysPower> powerList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return "1".equals(this.getStatus()) ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "1".equals(this.getEnable()) ? true : false;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
