package com.pearadmin.modules.sys.controller;

import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.web.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Druid 数据监控页面
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@RestController
@Api(tags = {"数据监控"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "druid")
public class SysDruidController extends BaseController {

    private final String MODULE_PATH = "system/druid/";

    /**
     * Druid 首页
     * */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/druid/main','sys:druid:main')")
    public ModelAndView main() {
        return jumpPage(MODULE_PATH + "main");
    }
}
