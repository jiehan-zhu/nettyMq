package com.pearadmin.modules.mq.service;

import com.netty.mq.util.ReturnT;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;

/**
 * registryService
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public interface MqCommonRegistryService {

    /**
     * refresh registry-value, check update and broacase
     */
    ReturnT<String> registry(String accessToken, List<MqCommonRegistryData> xxlCommonRegistryDataList);

    /**
     * remove registry-value, check update and broacase
     */
    ReturnT<String> remove(String accessToken, List<MqCommonRegistryData> xxlCommonRegistryDataList);

    /**
     * discovery registry-data, read file
     */
    ReturnT<Map<String, List<String>>> discovery(String accessToken, List<String> keys);

    /**
     * monitor update
     */
    DeferredResult<ReturnT<String>> monitor(String accessToken, List<String> keys);

}
