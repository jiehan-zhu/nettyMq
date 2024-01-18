package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pearadmin.common.web.base.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("SysDataSource")
@TableName("sys_data_source")
@EqualsAndHashCode(callSuper = true)
public class SysDataSource extends BaseDomain {

    /**
     * 编号
     * */
    @TableId("id")
    private String id;

    /**
     * 名称
     * */
    @TableField("name")
    private String name;

    /**
     * 是否启用
     * */
    @TableField("enable")
    private Boolean enable;

    /**
     * 账户
     * */
    @TableField("username")
    private String username;

    /**
     * 密码
     * */
    @TableField("password")
    private String password;

    /**
     * 链接
     */
    @TableField("url")
    private String url;

    /**
     * 驱动
     * */
    @TableField("driver")
    private String driver;

}