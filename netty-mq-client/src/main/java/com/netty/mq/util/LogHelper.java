package com.netty.mq.util;

import java.text.MessageFormat;
import java.util.Date;

/**
 * log
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class LogHelper {

    private static final String LOG_TEMPLATE = "<br><br>  <span style=\"color:#00c0ef;\" > >>>>>>>>>>> {0} <<<<<<<<<<< </span>  <br>时间：{1}  <br>备注：{2}";

    /**
     * make log
     */
    public static String makeLog(String logTitle, String logContent){

        String tim = DateUtil.formatDateTime(new Date());
        String log = MessageFormat.format(LOG_TEMPLATE, logTitle, tim, logContent);

        // sub
        if (log.length() > 20000) {
            log = log.substring(0, 20000) + "...";
        }

        return log;
    }

}
