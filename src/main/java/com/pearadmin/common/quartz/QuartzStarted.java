package com.pearadmin.common.quartz;

import com.pearadmin.modules.job.domain.ScheduleJob;
import com.pearadmin.modules.job.mapper.ScheduleJobMapper;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 定时任务启动处理类
 * Author: 就眠仪式
 * CreateTime: 2019/10/23
 */
public class QuartzStarted {

    @Resource
    private Scheduler scheduler;

    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = scheduleJobMapper.selectList(null);
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = QuartzService.getCronTrigger(scheduler, Long.parseLong(scheduleJob.getJobId()));
            if (cronTrigger == null) {
                QuartzService.createJob(scheduler, scheduleJob);
            } else {
                QuartzService.updateJob(scheduler, scheduleJob);
            }
        }
    }
}
