package com.pearadmin.modules.mq.controller;

import com.github.pagehelper.PageInfo;
import com.netty.mq.MqMessage;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.mq.service.MqMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;

/**
 * 存放所有消息Controller
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@RestController
@RequestMapping("/system/message")
public class MqMessageController extends BaseController {

    private String prefix = "system/message";

    @Autowired
    private MqMessageService mqMessageService;

    @GetMapping("/main")
    @PreAuthorize("hasPermission('/system/message/main','system:message:main')")
    public ModelAndView main() {
        return jumpPage(prefix + "/main");
    }

    /**
     * 查询存放所有消息列表
     */
    @ResponseBody
    @GetMapping("/data")
    @PreAuthorize("hasPermission('/system/message/data','system:message:data')")
    public ResultTable list(@ModelAttribute MqMessage mqMessage, PageDomain pageDomain) {
        PageInfo<MqMessage> pageInfo = mqMessageService.selectMqMessagePage(mqMessage, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 新增存放所有消息
     */
    @GetMapping("/add")
    @PreAuthorize("hasPermission('/system/message/add','system:message:add')")
    public ModelAndView add() {
        return jumpPage(prefix + "/add");
    }

    /**
     * 新增存放所有消息
     */
    @ResponseBody
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/system/message/add','system:message:add')")
    public Result save(@RequestBody MqMessage mqMessage) {
        return decide(mqMessageService.save(Collections.singletonList(mqMessage)));
    }

    /**
     * 修改存放所有消息
     */
    @GetMapping("/edit")
    @PreAuthorize("hasPermission('/system/message/edit','system:message:edit')")
    public ModelAndView edit(Integer id, ModelMap map) {
        MqMessage mqMessage =mqMessageService.getById(id);
        map.put("mqMessage", mqMessage);
        return jumpPage(prefix + "/edit");
    }

    /**
     * 修改存放所有消息
     */
    @ResponseBody
    @PutMapping("/update")
    @PreAuthorize("hasPermission('/system/message/edit','system:message:edit')")
    public Result update(@RequestBody MqMessage mqMessage) {
        return decide(mqMessageService.update(mqMessage));
    }

    /**
     * 删除存放所有消息
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/system/message/remove','system:message:remove')")
    public Result batchRemove(String ids) {
        return decide(mqMessageService.removeByIds(Arrays.asList(ids.split(","))));
    }

    /**
     * 删除存放所有消息
     */
    @ResponseBody
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/system/message/remove','system:message:remove')")
    public Result remove(@PathVariable("id") Long id) {
        return decide(mqMessageService.removeById(id));
    }
}
