package com.pearadmin.modules.mq.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.tools.HttpClientUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.mq.domain.MqConsumerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.pearadmin.modules.mq.mapper.MqCommonRegistryDataMapper;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import com.pearadmin.modules.mq.service.MqCommonRegistryDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 已上线的消费者Service业务层处理
 *
 * @author zjh
 * @date 2024-01-22
 */
@Service
public class MqCommonRegistryDataServiceImpl extends ServiceImpl<MqCommonRegistryDataMapper,MqCommonRegistryData> implements MqCommonRegistryDataService {
    private static Logger logger = LoggerFactory.getLogger(MqCommonRegistryDataServiceImpl.class);

    private static final String SpaceMark = "_consumer_";
    /**
     * 请求web系统返回值 成功为true
     */
    private static final String REQUEST_SUCCESS_RESULT = "true";

    /**
     * 查询已上线的消费者
     * @param mqCommonRegistryData 已上线的消费者
     * @param pageDomain
     * @return 已上线的消费者 分页集合
     * */
    @Override
    public PageInfo<MqCommonRegistryData> selectMqCommonRegistryDataPage(MqCommonRegistryData mqCommonRegistryData, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<MqCommonRegistryData> data = baseMapper.selectMqCommonRegistryDataList(mqCommonRegistryData);
        return new PageInfo<>(data);
    }



    @Override
    public boolean consumerStatus(MqConsumerDto dto) {
        MqCommonRegistryData data = baseMapper.selectById(dto.getConsumerId());
        String[] valueArray = data.getValue().split(SpaceMark);
        String consumerKey =  valueArray[valueArray.length - 1] + data.getKey().substring(10) + valueArray[0];
        String url = "http://"+data.getIp()+":"+dto.getPort()+"/consumerStatus?consumerKey="+consumerKey+"&status="+dto.getConsumerStatus();
        //发送get请求
        String getResult = HttpClientUtil.doGet(url);
        logger.info("IXxlMqConsumerServiceImpl-consumerStop:getUrl为{} getResult为{}", url, getResult);
        //处理请求结果
        if (REQUEST_SUCCESS_RESULT.equals(getResult)) {
            int row = baseMapper.updateStatus(dto.getConsumerId(), dto.getConsumerStatus());
            return row > 0;
        }
        return false;
    }

    @Override
    public boolean consumerAdd(MqConsumerDto dto) {
        String url = "http://"+dto.getIp()+":"+dto.getPort()+"/consumerAdd?consumerClassName="+dto.getConsumerClassName()+"&addNum="+dto.getNumber();
        String result = HttpClientUtil.doGet(url);
        return REQUEST_SUCCESS_RESULT.equals(result);
    }


}
