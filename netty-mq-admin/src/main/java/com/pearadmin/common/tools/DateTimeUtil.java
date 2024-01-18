package com.pearadmin.common.tools;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * Describe: 日 期 工 具 类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public class DateTimeUtil {

    /**
     * 获启动时间
     *
     * @return {@link Date}
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算时间差
     *
     * {@link String}
     * */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}
