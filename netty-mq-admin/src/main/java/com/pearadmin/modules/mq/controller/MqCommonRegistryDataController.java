package com.pearadmin.modules.mq.controller;

import com.github.pagehelper.PageInfo;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.mq.domain.MqConsumerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.pearadmin.modules.mq.service.MqCommonRegistryDataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 已上线的消费者Controller
 *
 * @author zjh
 * @date 2024-01-22
 */
@RestController
@RequestMapping("/mq/commonRegistryData")
public class MqCommonRegistryDataController extends BaseController {

    private String prefix = "mq/registry";
    private static final String SpaceMark = "_consumer_";

    @Autowired
    private MqCommonRegistryDataService mqCommonRegistryDataService;

    @GetMapping("/main")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/main','mq:commonRegistryData:main')")
    public ModelAndView main() {
        return jumpPage(prefix + "/main");
    }

    /**
     * 查询已上线的消费者列表
     */
    @ResponseBody
    @GetMapping("/data")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/data','mq:commonRegistryData:data')")
    public ResultTable list(@ModelAttribute MqCommonRegistryData mqCommonRegistryData, PageDomain pageDomain) {
        PageInfo<MqCommonRegistryData> pageInfo = mqCommonRegistryDataService.selectMqCommonRegistryDataPage(mqCommonRegistryData, pageDomain);

        List<MqConsumerDto> consumerList = new ArrayList<>();
        for (MqCommonRegistryData MqCommonRegistryData : pageInfo.getList()) {
            String key = MqCommonRegistryData.getKey();
            String value = MqCommonRegistryData.getValue();
            if (value != null && key != null && key.contains(SpaceMark)) {
                MqConsumerDto MqConsumerDto = new MqConsumerDto();
                String[] valueArray = value.split(SpaceMark);
                MqConsumerDto.setConsumerId(MqCommonRegistryData.getId());
                MqConsumerDto.setConsumerRegistryKey(key);
                MqConsumerDto.setConsumerRegistryValue(value);
                MqConsumerDto.setConsumerGroup(valueArray[0]);
                MqConsumerDto.setConsumerTopic(key.substring(10));
                MqConsumerDto.setConsumerThreadUuid(valueArray[valueArray.length - 1]);
                MqConsumerDto.setIp(MqCommonRegistryData.getIp());
                MqConsumerDto.setConsumerStatus(MqCommonRegistryData.getStatus());
                consumerList.add(MqConsumerDto);
            }
        }

        return pageTable(consumerList, pageInfo.getTotal()-1);
    }

    /**
     * 新增已上线的消费者
     */
    @GetMapping("/add")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/add','mq:commonRegistryData:add')")
    public ModelAndView add() {
        return jumpPage(prefix + "/add");
    }

    /**
     * 新增已上线的消费者
     */
    @ResponseBody
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/add','mq:commonRegistryData:add')")
    public Result save(@RequestBody MqConsumerDto dto) {
        return decide(mqCommonRegistryDataService.consumerAdd(dto));
    }

    /**
     * 修改已上线的消费者
     */
    @GetMapping("/edit")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/edit','mq:commonRegistryData:edit')")
    public ModelAndView edit(Long id, ModelMap map) {
        MqCommonRegistryData mqCommonRegistryData =mqCommonRegistryDataService.getById(id);
        map.put("mqCommonRegistryData", mqCommonRegistryData);
        return jumpPage(prefix + "/edit");
    }

    /**
     * 修改已上线的消费者
     */
    @ResponseBody
    @PutMapping("/update")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/edit','mq:commonRegistryData:edit')")
    public Result update(@RequestBody MqConsumerDto dto) {
        return decide(mqCommonRegistryDataService.consumerStatus(dto));
    }

    /**
     * 删除已上线的消费者
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/remove','mq:commonRegistryData:remove')")
    public Result batchRemove(String ids) {
        return decide(mqCommonRegistryDataService.removeByIds(Arrays.asList(ids.split(","))));
    }

    /**
     * 删除已上线的消费者
     */
    @ResponseBody
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/mq/commonRegistryData/remove','mq:commonRegistryData:remove')")
    public Result remove(@PathVariable("id") Long id) {
        return decide(mqCommonRegistryDataService.removeById(id));
    }
}
