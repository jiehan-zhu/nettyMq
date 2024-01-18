package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

/**
 * 角色部门映射关系
 * <p>
 * 提供便于操作 IOC 的 API
 *
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Data
@Alias("SysRoleDept")
public class SysRoleDept {

    /**
     * 编号
     * */
    @TableId
    private String id;

    /**
     * 角色编号
     * */
    private String roleId;

    /**
     * 部门编号
     * */
    private String deptId;

}
