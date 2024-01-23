package com.pearadmin.modules.mq.service;

import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.modules.mq.domain.MqConsumerDto;

/**
 * 已上线的消费者Service接口
 *
 * @author zjh
 * @date 2024-01-22
 */
public interface MqCommonRegistryDataService extends IService<MqCommonRegistryData> {

    /**
     * 查询已上线的消费者
     * @param mqCommonRegistryData 已上线的消费者
     * @param pageDomain
     * @return 已上线的消费者 分页集合
     * */
    PageInfo<MqCommonRegistryData> selectMqCommonRegistryDataPage(MqCommonRegistryData mqCommonRegistryData, PageDomain pageDomain);


    /**
     * 消费者启停
     *
     * @param dto 消费者对象
     * @return ReturnT
     */
    boolean consumerStatus(MqConsumerDto dto);

    /**
     * 手动添加消费者
     *
     * @param consumerClassName 消费者全限定类名
     * @param addNum            添加数量
     * @return ReturnT
     */
    boolean consumerAdd(MqConsumerDto dto);


}
