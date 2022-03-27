package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 部门接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {


    /**
     * 根据 userId 获取部门列表
     *
     * @param userId 用户编号
     * @return {@link SysDept}
     * */
    List<SysDept> selectDeptByUserId(String userId);

}
