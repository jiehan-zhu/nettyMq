package com.pearadmin.modules.mq.mapper;

import com.pearadmin.modules.mq.domain.MqCommonRegistry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Mapper
public interface MqCommonRegistryMapper {

    public List<MqCommonRegistry> pageList(@Param("offset") int offset, @Param("pagesize") int pagesize);

    public MqCommonRegistry load(@Param("key") String key);

    public int add(@Param("MqCommonRegistry") MqCommonRegistry MqCommonRegistry);

    public int update(@Param("MqCommonRegistry") MqCommonRegistry MqCommonRegistry);

    public int cleanDead();

}
