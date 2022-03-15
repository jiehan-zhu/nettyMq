package com.pearadmin.common.configure;

import cn.hutool.extra.mail.MailAccount;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.pearadmin.common.constant.ConfigurationConstant;
import com.pearadmin.common.web.interceptor.PreviewInterceptor;
import com.pearadmin.common.web.interceptor.RateLimitInterceptor;
import com.pearadmin.modules.sys.service.SysConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Core 核心配置
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Configuration
public class CoreConfig implements WebMvcConfigurer {

    @Resource
    private SysConfigService sysContext;

    @Resource
    private RateLimitInterceptor rateLimitInterceptor;

    @Resource
    private PreviewInterceptor previewInterceptor;

    @Bean
    public MailAccount mailAccount() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(sysContext.getConfig(ConfigurationConstant.MAIN_HOST));
        mailAccount.setPort(sysContext.getConfig(ConfigurationConstant.MAIN_PORT) == "" ? 0000 : Integer.parseInt(sysContext.getConfig(ConfigurationConstant.MAIN_PORT)));
        mailAccount.setFrom(sysContext.getConfig(ConfigurationConstant.MAIN_FROM));
        mailAccount.setUser(sysContext.getConfig(ConfigurationConstant.MAIN_USER));
        mailAccount.setPass(sysContext.getConfig(ConfigurationConstant.MAIN_PASS));
        mailAccount.setCharset(StandardCharsets.UTF_8);
        mailAccount.setAuth(true);
        return mailAccount;
    }

    @Bean
    public Module dateTime() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return javaTimeModule;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new LongModule());
        objectMapper.registerModule(new SimpleModule());
        objectMapper.registerModule(dateTime());
        return objectMapper;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(previewInterceptor).addPathPatterns("/**");
    }
}
