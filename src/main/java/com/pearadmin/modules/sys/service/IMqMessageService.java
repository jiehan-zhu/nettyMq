package com.pearadmin.modules.sys.service;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.MqMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 存放所有消息Service接口
 *
 * @author 祝杰汉
 * @date 2023-12-08
 */
public interface IMqMessageService extends IService<MqMessage> {

    /**
     * 查询存放所有消息
     * @param mqMessage 存放所有消息
     * @param pageDomain
     * @return 存放所有消息 分页集合
     * */
    PageInfo<MqMessage> selectMqMessagePage(MqMessage mqMessage, PageDomain pageDomain);

}
