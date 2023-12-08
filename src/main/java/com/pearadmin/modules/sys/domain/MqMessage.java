package com.pearadmin.modules.sys.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.pearadmin.common.web.base.BaseDomain;

/**
 * 存放所有消息实体
 *
 * @author 祝杰汉
 * @date 2023-12-08
 */
@Data
//@TableName("mq_message")
public class MqMessage  {

    /** 主键 */
    @TableId
    private Long id;

    /** 主题 */
    private String topic;

    /** 分组 */
    @TableField("`group`")
    private String group;

    /** 消息 */
    @TableField("`data`")
    private String data;

    /** 状态 */
    @TableField("`status`")
    private String status;

    /** 重试次数 */
    private Long retryCount;

    /** 分片id */
    private Long shardingId;

    /** 超时时间 */
    private Long timeout;

    /** 生效时间 */
    private Date effectTime;

    /** 创建时间 */
    private Date addTime;

    /** 消费日志 */
    private String log;


}