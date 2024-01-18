package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysRoleDept;
import com.pearadmin.modules.sys.domain.SysRolePower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {


    int deleteByRoleId(String roleId);

    int batchInsert(List<SysRoleDept> sysRoleDepts);

}
