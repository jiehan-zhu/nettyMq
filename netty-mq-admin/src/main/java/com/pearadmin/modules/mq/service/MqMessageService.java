package com.pearadmin.modules.mq.service;

import com.github.pagehelper.PageInfo;
import com.netty.mq.MqMessage;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 存放所有消息Service接口
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public interface MqMessageService extends IService<MqMessage> {

    /**
     * 查询存放所有消息
     * @param mqMessage 存放所有消息
     * @param pageDomain
     * @return 存放所有消息 分页集合
     * */
    PageInfo<MqMessage> selectMqMessagePage(MqMessage mqMessage, PageDomain pageDomain);


    boolean save(List<MqMessage> mqList);

    MqMessage getById(Integer id);

    boolean update(MqMessage mqMessage);



}
