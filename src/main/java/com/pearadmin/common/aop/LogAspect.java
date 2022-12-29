package com.pearadmin.common.aop;

import com.pearadmin.common.aop.annotation.Log;
import com.pearadmin.common.aop.enums.LoggingType;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.modules.sys.domain.SysLog;
import com.pearadmin.modules.sys.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Describe: 日 志 切 面 实 现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private SysLogService sysLogService;

    /**
     * 切 面 编 程
     */
    @Pointcut("@annotation(com.pearadmin.common.aop.annotation.Log) || @within(com.pearadmin.common.aop.annotation.Log)")
    public void dsPointCut() {
    }

    /**
     * 处 理 系 统 日 志
     */
    @Around("dsPointCut()")
    private Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLog sysLog = new SysLog();
        Object result;
        try {
            Log logAnnotation = getLogging(joinPoint);
            sysLog.setId(SequenceUtil.makeStringId());
            sysLog.setTitle(logAnnotation.value());
            sysLog.setTitle(logAnnotation.title());
            sysLog.setDescription(logAnnotation.describe());
            sysLog.setBusinessType(logAnnotation.type());
            sysLog.setSuccess(true);
            sysLog.setLoggingType(LoggingType.OPERATE);
            result = joinPoint.proceed();
        } catch (Exception exception) {
            sysLog.setSuccess(false);
            sysLog.setErrorMsg(exception.getMessage());
            throw exception;
        } finally {
            // TODO 记录日志
            sysLogService.save(sysLog);
        }
        return result;
    }

    /**
     * 获 取 注 解
     */
    public Log getLogging(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        Log targetLog = targetClass.getAnnotation(Log.class);
        if (targetLog != null) {
            return targetLog;
        } else {
            Method method = signature.getMethod();
            Log log = method.getAnnotation(Log.class);
            return log;
        }
    }
}
