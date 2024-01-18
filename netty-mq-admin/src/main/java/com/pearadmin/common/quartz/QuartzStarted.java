package com.pearadmin.common.quartz;

import com.pearadmin.modules.job.domain.ScheduleJob;
import com.pearadmin.modules.job.mapper.ScheduleJobMapper;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Quartz Started
 * <p>
 * Spring 初始化时检测任务, 加载 Job 任务到内存
 *
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
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
