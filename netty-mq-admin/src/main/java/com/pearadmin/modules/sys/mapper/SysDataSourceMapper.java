package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDataSourceMapper extends BaseMapper<SysDataSource> {

    /**
     * 获取多库列表
     *
     * @return {@link SysDataSource}
     * */
    List<SysDataSource> selectDataSource(@Param("request") SysDataSource request);
}
