package com.pearadmin.modules.mq.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.netty.mq.MqMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 存放所有消息Mapper接口
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@Mapper
public interface MqMessageMapper extends BaseMapper<MqMessage> {
    /**
     * 查询存放所有消息列表
     *
     * @param mqMessage 存放所有消息
     * @return 存放所有消息集合
     */
    List<MqMessage> selectMqMessageList(MqMessage mqMessage);



//    // ---------------------- broker api ----------------------

    boolean save(@Param("messageList") List<MqMessage> messageList);


    MqMessage getById(Integer Id);

    boolean update(MqMessage mqMessage);


//    public List<MqMessage> pullNewMessage(@Param("newStatus") String newStatus,
//                                               @Param("topic") String topic,
//                                               @Param("group") String group,
//                                               @Param("consumerRank") int consumerRank,
//                                               @Param("consumerTotal") int consumerTotal,
//                                               @Param("pagesize") int pagesize);
//
//    public int lockMessage(@Param("id") long id,
//                           @Param("appendLog") String appendLog,
//                           @Param("newStatus") String newStatus,
//                           @Param("ingStatus") String ingStatus);
//
//    public int updateStatus(@Param("messageList") List<MqMessage> messageList);
//
//
//    // ---------------------- broker service ----------------------
//
//    /**
//     * retry message, retryCount -1 and status from fail to new
//     */
//    public int updateRetryCount(@Param("failStatus") String failStatus,
//                                @Param("newStatus") String newStatus,
//                                @Param("appendLog") String appendLog);
//
//    /**
//     * clean success message before the days
//     */
//    public int cleanSuccessMessage(@Param("successStatus") String successStatus, @Param("logretentiondays") int logretentiondays);
//
//
//    /**
//     * find new topic not in topic-table
//     */
//    public List<String> findNewTopicList();
//
//    /**
//     * message info by day
//     */
//    public List<Map<String,Object>> messageCountByDay(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//    /**
//     * clean message
//     */
//    public int clearMessage(@Param("topic") String topic, @Param("status") String status, @Param("type") int type);
//
//    /**
//     * reset block timeout message, reset status from RUNNING to FAIL
//     */
//    public int resetBlockTimeoutMessage(@Param("ingStatus") String ingStatus, @Param("failStatus") String failStatus, @Param("appendLog") String appendLog);
//
//


}
