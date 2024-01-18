//package com.pearadmin.modules.mq.domain;
//
//import java.util.Date;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import lombok.Data;
//
///**
// * 存放所有消息实体
// *
// * @author 祝杰汉
// * @date 2024-01-18
// */
//@Data
////@TableName("mq_message")
//public class MqMessage  {
//
//    /** 主键 */
//    @TableId(type = IdType.AUTO)
//    private Integer id;
//
//    /** 主题 */
//    private String topic;
//
//    /** 分组 */
//    @TableField("`group`")
//    private String group;
//
//    /** 消息 */
//    @TableField("`data`")
//    private String data;
//
//    /** 状态 */
//    @TableField("`status`")
//    private String status;
//
//    /** 重试次数 */
//    private Integer retryCount;
//
//    /** 分片id */
//    private Integer shardingId;
//
//    /** 超时时间 */
//    private Integer timeout;
//
//    /** 生效时间 */
//    private Date effectTime;
//
//    /** 创建时间 */
//    private Date addTime;
//
//    /** 消费日志 */
//    private String log;
//
//
//}