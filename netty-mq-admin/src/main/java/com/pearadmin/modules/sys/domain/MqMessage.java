//package com.pearadmin.modules.sys.domain;
//
//import java.util.Date;
//
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import lombok.Data;
//
///**
// * 存放所有消息实体
// *
// * @author 祝杰汉
// * @date 2023-12-08
// */
//@Data
////@TableName("mq_message")
//public class MqMessage  {
//
//    /** 主键 */
//    @TableId
//    private Long id;
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
//    public MqMessage() {
//    }
//
//    public MqMessage(String topic, String data) {
//        this.topic = topic;
//        this.data = data;
//    }
//
//    public MqMessage(String topic, String data, Date effectTime) {
//        this.topic = topic;
//        this.data = data;
//        this.effectTime = effectTime;
//    }
//
//    public MqMessage(String topic, String data, Integer shardingId) {
//        this.topic = topic;
//        this.data = data;
//        this.shardingId = shardingId;
//    }
//
//    // for clone
//    public MqMessage(MqMessage MqMessage) {
//        this.id = MqMessage.id;
//        this.topic = MqMessage.topic;
//        this.group = MqMessage.group;
//        this.data = MqMessage.data;
//        this.status = MqMessage.status;
//        this.retryCount = MqMessage.retryCount;
//        this.shardingId = MqMessage.shardingId;
//        this.timeout = MqMessage.timeout;
//        this.effectTime = MqMessage.effectTime;
//        this.addTime = MqMessage.addTime;
//        this.log = MqMessage.log;
//    }
//
//
//    @Override
//    public String toString() {
//        return "MqMsg{" +
//                "id=" + id +
//                ", shardingId=" + shardingId +
//                ", group='" + group + '\'' +
//                ", topic='" + topic + '\'' +
//                ", data='" + data + '\'' +
////				", status='" + status + '\'' +
////				", retryCount=" + retryCount +
////				", timeout=" + timeout +
////				", effectTime=" + effectTime +
////				", addTime=" + addTime +
////				", log='" + log + '\'' +
//                '}';
//    }
//
//}