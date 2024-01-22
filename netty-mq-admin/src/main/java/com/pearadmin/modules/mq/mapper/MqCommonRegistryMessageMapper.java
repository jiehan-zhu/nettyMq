package com.pearadmin.modules.mq.mapper;

import com.pearadmin.modules.mq.domain.MqCommonRegistryMessage;
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
public interface MqCommonRegistryMessageMapper {

     int add(@Param("mqCommonRegistryMessage") MqCommonRegistryMessage mqCommonRegistryMessage);

     List<MqCommonRegistryMessage> findMessage(@Param("excludeIds") List<Integer> excludeIds);

     int cleanMessage(@Param("messageTimeout") int messageTimeout);

}
