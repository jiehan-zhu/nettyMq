package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysMail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Describe: 邮件服务持久层接口
 * Author: Heiky
 * CreateTime: 2021/1/13 14:51
 */
@Mapper
public interface SysMailMapper extends BaseMapper<SysMail> {

    /**
     * 根据条件查询邮件列表
     *
     * @param sysMail
     * @return list
     */
    List<SysMail> selectList(SysMail sysMail);

}
