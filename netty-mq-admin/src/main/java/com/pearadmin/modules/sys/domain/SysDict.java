package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pearadmin.common.web.base.BaseDomain;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * Describe: 字典类型领域模型
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */

@Data
@Alias("SysDict")
public class SysDict extends BaseDomain {

    /**
     * 标识
     */
    @TableId
    private String id;

    /**
     * 字典名称
     */
    private String typeName;

    /**
     * 字典类型
     */
    private String typeCode;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 字典可用状态
     */
    private String enable;

}
