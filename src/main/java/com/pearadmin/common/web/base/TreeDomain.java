package com.pearadmin.common.web.base;

import lombok.Data;

@Data
public class TreeDomain extends BaseDomain {

    private String parentId;

    private String parentName;
}
