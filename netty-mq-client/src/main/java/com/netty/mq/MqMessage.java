package com.netty.mq;



import java.util.Date;

/**
 * 存放所有消息实体
 *
 * @author 祝杰汉
 * @date 2023-12-08
 */
//@TableName("mq_message")
public class MqMessage {

    /** 主键 */
    private Integer id;

    /** 主题 */
    private String topic;

    /** 分组 */
    private String group;

    /** 消息 */
    private String data;

    /** 状态 */
    private String status;

    /** 重试次数 */
    private Integer retryCount;

    /** 分片id */
    private Integer shardingId;

    /** 超时时间 */
    private Integer timeout;

    /** 生效时间 */
    private Date effectTime;

    /** 创建时间 */
    private Date addTime;

    /** 消费日志 */
    private String log;
    public MqMessage() {
    }

    public MqMessage(String topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    public MqMessage(String topic, String data, Date effectTime) {
        this.topic = topic;
        this.data = data;
        this.effectTime = effectTime;
    }

    public MqMessage(String topic, String data, Integer shardingId) {
        this.topic = topic;
        this.data = data;
        this.shardingId = shardingId;
    }

    // for clone
    public MqMessage(MqMessage MqMessage) {
        this.id = MqMessage.id;
        this.topic = MqMessage.topic;
        this.group = MqMessage.group;
        this.data = MqMessage.data;
        this.status = MqMessage.status;
        this.retryCount = MqMessage.retryCount;
        this.shardingId = MqMessage.shardingId;
        this.timeout = MqMessage.timeout;
        this.effectTime = MqMessage.effectTime;
        this.addTime = MqMessage.addTime;
        this.log = MqMessage.log;
    }


    @Override
    public String toString() {
        return "MqMsg{" +
                "id=" + id +
                ", shardingId=" + shardingId +
                ", group='" + group + '\'' +
                ", topic='" + topic + '\'' +
                ", data='" + data + '\'' +
//				", status='" + status + '\'' +
//				", retryCount=" + retryCount +
//				", timeout=" + timeout +
//				", effectTime=" + effectTime +
//				", addTime=" + addTime +
//				", log='" + log + '\'' +
                '}';
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRetryCount() {
        return null == retryCount ? 0 :retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getShardingId() {
        return null == shardingId ? 0 :shardingId;
    }

    public void setShardingId(Integer shardingId) {
        this.shardingId = shardingId;
    }

    public Integer getTimeout() {
        return null == timeout ? 0:timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}