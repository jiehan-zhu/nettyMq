package com.pearadmin.modules.mq.mapper;

import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
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
public interface MqCommonRegistryDataMapper {


    public int refresh(@Param("mqCommonRegistryData") MqCommonRegistryData mqCommonRegistryData);

    public int add(@Param("mqCommonRegistryData") MqCommonRegistryData mqCommonRegistryData);


    public List<MqCommonRegistryData> findData(@Param("key") String key);

    public int cleanData(@Param("timeout") int timeout);

    public int deleteDataValue(@Param("key") String key,
                               @Param("value") String value);

    public int count();

    // 精确查询 topic
    public List<MqCommonRegistryData> pageList(@Param("offset") int offset,
                                               @Param("pagesize") int pagesize,
                                               @Param("key") String key);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("key") String key);

    public int updateStatus(@Param("id") long id, @Param("status") String status);
}
