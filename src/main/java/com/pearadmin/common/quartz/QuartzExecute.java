package com.pearadmin.common.quartz;

import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.context.BeanContext;
import com.pearadmin.modules.job.domain.ScheduleJob;
import com.pearadmin.modules.job.domain.ScheduleLog;
import com.pearadmin.modules.job.service.IScheduleLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Quartz Job 执行逻辑
 * <p>
 * 通过 className 反射生成实例, 执行任务并记录日志
 *
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Slf4j
public class QuartzExecute extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Object o = context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        ScheduleJob jobBean = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        IScheduleLogService scheduleJobLogService = (IScheduleLogService) BeanContext.getBean("scheduleLogService");
        ScheduleLog logBean = new ScheduleLog();
        logBean.setLogId(SequenceUtil.makeStringId());
        logBean.setJobId(jobBean.getJobId());
        logBean.setBeanName(jobBean.getBeanName());
        logBean.setParams(jobBean.getParams());
        logBean.setCreateTime(LocalDateTime.now());
        long beginTime = System.currentTimeMillis();
        try {
            Object target = BeanContext.getBean(jobBean.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, jobBean.getParams());
            long executeTime = System.currentTimeMillis() - beginTime;
            logBean.setTimes((int) executeTime);
            logBean.setStatus(0);
            log.info("定时器 === >> " + jobBean.getJobName() + "执行成功,耗时 === >> " + executeTime);
        } catch (Exception e) {
            long executeTime = System.currentTimeMillis() - beginTime;
            logBean.setTimes((int) executeTime);
            logBean.setStatus(1);
            logBean.setError(e.getMessage());
            e.getCause();
        } finally {
            scheduleJobLogService.insert(logBean);
        }
    }
}
