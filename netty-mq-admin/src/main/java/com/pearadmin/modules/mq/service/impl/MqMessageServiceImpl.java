package com.pearadmin.modules.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.netty.mq.MqMessage;
import com.pearadmin.modules.mq.mapper.MqMessageMapper;
import com.pearadmin.modules.mq.service.MqMessageService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 存放所有消息Service业务层处理
 *
 * @author 祝杰汉
 * @date 2023-12-08
 */
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements MqMessageService {


    /**
     * 查询存放所有消息
     * @param mqMessage 存放所有消息
     * @param pageDomain
     * @return 存放所有消息 分页集合
     * */
    @Override
    public PageInfo<MqMessage> selectMqMessagePage(MqMessage mqMessage, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<MqMessage> data = baseMapper.selectMqMessageList(mqMessage);
        return new PageInfo<>(data);
    }

    @Override
    public boolean save(List<MqMessage> mqList) {
        return baseMapper.save(mqList);
    }

    @Override
    public MqMessage getById(Integer id) {
        return baseMapper.getById(id);
    }

    @Override
    public boolean update(MqMessage mqMessage) {
        return baseMapper.update(mqMessage);
    }


}
