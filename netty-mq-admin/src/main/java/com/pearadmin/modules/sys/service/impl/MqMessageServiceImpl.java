//package com.pearadmin.modules.sys.service.impl;
//
//import java.util.List;
//import java.util.ArrayList;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.pearadmin.common.web.domain.request.PageDomain;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.pearadmin.modules.sys.mapper.MqMessageMapper;
//import com.netty.mq.MqMessage;
//import com.pearadmin.modules.sys.service.MqMessageService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//
//import java.util.Arrays;
///**
// * 存放所有消息Service业务层处理
// *
// * @author 祝杰汉
// * @date 2023-12-08
// */
//@Service
//public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper,MqMessage> implements MqMessageService {
//
//
//    /**
//     * 查询存放所有消息
//     * @param mqMessage 存放所有消息
//     * @param pageDomain
//     * @return 存放所有消息 分页集合
//     * */
//    @Override
//    public PageInfo<MqMessage> selectMqMessagePage(MqMessage mqMessage, PageDomain pageDomain) {
//        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
//        List<MqMessage> data = baseMapper.selectMqMessageList(mqMessage);
//        return new PageInfo<>(data);
//    }
//
//}
