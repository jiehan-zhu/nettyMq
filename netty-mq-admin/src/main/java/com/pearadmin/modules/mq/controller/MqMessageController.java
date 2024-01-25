package com.pearadmin.modules.mq.controller;

import com.github.pagehelper.PageInfo;
import com.netty.mq.MqMessage;
import com.netty.mq.util.LogHelper;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.modules.mq.service.MqMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 存放所有消息Controller
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@RestController
@RequestMapping("/mq/message")
public class MqMessageController extends BaseController {

    private String prefix = "mq/message";

    @Autowired
    private MqMessageService mqMessageService;

    @GetMapping("/main")
    @PreAuthorize("hasPermission('/mq/message/main','mq:message:main')")
    public ModelAndView main() {
        return jumpPage(prefix + "/main");
    }

    /**
     * 查询存放所有消息列表
     */
    @ResponseBody
    @GetMapping("/data")
    @PreAuthorize("hasPermission('/mq/message/data','mq:message:data')")
    public ResultTable list(@ModelAttribute MqMessage mqMessage, PageDomain pageDomain) {
        PageInfo<MqMessage> pageInfo = mqMessageService.selectMqMessagePage(mqMessage, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 新增存放所有消息
     */
    @GetMapping("/add")
    @PreAuthorize("hasPermission('/mq/message/add','mq:message:add')")
    public ModelAndView add() {
        return jumpPage(prefix + "/add");
    }

    /**
     * 新增存放所有消息
     */
    @ResponseBody
    @PostMapping("/save")
    @PreAuthorize("hasPermission('/mq/message/add','mq:message:add')")
    public Result save(@RequestBody MqMessage mqMessage) {
        List<MqMessage> list = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(mqMessage.getLog()); i++) {
            MqMessage newMsg = new MqMessage();
            BeanUtils.copyProperties(mqMessage,newMsg);
            // log
            String appendLog = LogHelper.makeLog("手动添加", newMsg.toString());
            newMsg.setLog(appendLog);
            list.add(newMsg);
        }
        return decide(mqMessageService.save(list));
    }

    /**
     * 修改存放所有消息
     */
    @GetMapping("/edit")
    @PreAuthorize("hasPermission('/mq/message/edit','mq:message:edit')")
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
    @PreAuthorize("hasPermission('/mq/message/edit','mq:message:edit')")
    public Result update(@RequestBody MqMessage mqMessage) {
        // log
        String appendLog = LogHelper.makeLog("手动修改", mqMessage.toString());
        mqMessage.setLog(appendLog);
        return decide(mqMessageService.update(mqMessage));
    }

    /**
     * 删除存放所有消息
     */
    @ResponseBody
    @DeleteMapping("/batchRemove")
    @PreAuthorize("hasPermission('/mq/message/remove','mq:message:remove')")
    public Result batchRemove(String ids) {
        return decide(mqMessageService.removeByIds(Arrays.asList(ids.split(","))));
    }

    /**
     * 删除存放所有消息
     */
    @ResponseBody
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasPermission('/mq/message/remove','mq:message:remove')")
    public Result remove(@PathVariable("id") Long id) {
        return decide(mqMessageService.removeById(id));
    }
}
