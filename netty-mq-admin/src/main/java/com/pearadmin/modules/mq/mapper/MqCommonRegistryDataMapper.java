package com.pearadmin.modules.mq.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 已上线的消费者Mapper接口
 *
 * @author zjh
 * @date 2024-01-22
 */
@Mapper
public interface MqCommonRegistryDataMapper extends BaseMapper<MqCommonRegistryData> {
    /**
     * 查询已上线的消费者列表
     *
     * @param mqCommonRegistryData 已上线的消费者
     * @return 已上线的消费者集合
     */
    List<MqCommonRegistryData> selectMqCommonRegistryDataList(MqCommonRegistryData mqCommonRegistryData);

    int refresh(@Param("mqCommonRegistryData") MqCommonRegistryData mqCommonRegistryData);

    int add(@Param("mqCommonRegistryData") MqCommonRegistryData mqCommonRegistryData);


    List<MqCommonRegistryData> findData(@Param("key") String key);

    int cleanData(@Param("timeout") int timeout);

    int deleteDataValue(@Param("key") String key,
                               @Param("value") String value);

    int count();

    // 精确查询 topic
    List<MqCommonRegistryData> pageList(@Param("offset") int offset,
                                               @Param("pagesize") int pagesize,
                                               @Param("key") String key);

    int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("key") String key);

    int updateStatus(@Param("id") long id, @Param("status") String status);
}
