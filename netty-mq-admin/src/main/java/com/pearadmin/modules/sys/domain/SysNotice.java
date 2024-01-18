package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pearadmin.common.web.base.BaseDomain;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * notice对象 sys_notice
 *
 * @author jmys
 * @date 2021-03-13
 */
@Data
@Alias("SysNotice")
public class SysNotice extends BaseDomain {

    /** 编号 */
    @TableId
    private String id;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 发送人 */
    private String sender;

    /** 发送人 */
    @TableField(exist = false)
    private String senderName;

    /** 接收者 */
    private String accept;

    /** 接收人 */
    @TableField(exist = false)
    private String acceptName;

    /** 类型 */
    private String type;

}
