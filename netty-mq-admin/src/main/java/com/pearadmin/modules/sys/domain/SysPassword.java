package com.pearadmin.modules.sys.domain;

import lombok.Data;

/**
 * 用户修改密码接口参数实体
 *
 * @Author: Heiky
 * @Date: 2020/12/31 16:46
 * @Description:
 */
@Data
public class SysPassword {

    /**
     * 编号
     * */
    private String userId;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;

}
