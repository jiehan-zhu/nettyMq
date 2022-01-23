package com.pearadmin.common.secure;

import com.pearadmin.common.configure.proprety.SecurityProperty;
import com.pearadmin.common.secure.process.*;
import com.pearadmin.common.secure.support.SecureCaptchaSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.annotation.Resource;

/**
 * Security 配置
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperty.class)
public class SecureConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private SecureAuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Resource
    private SecureAuthenticationSuccessHandler securityAccessSuccessHandler;

    @Resource
    private SecureAuthenticationFailureHandler securityAccessFailureHandler;

    @Resource
    private SecureLogoutSuccessHandler securityAccessLogoutHandler;

    @Resource
    private SecureAccessDeniedHandler securityAccessDeniedHandler;

    @Resource
    private SecurityProperty securityProperty;

    @Resource
    private UserDetailsService securityUserDetailsService;

    @Resource
    private PersistentTokenRepository securityUserTokenService;

    @Resource
    private SecureCaptchaSupport securityCaptchaSupport;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SecureSessionExpiredHandler securityExpiredSessionHandler;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private SecureLogoutHandler securityLogoutHandler;

    @Resource
    private SecureRememberMeHandler secureRememberMeHandler;

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * Describe: 配置 Security 控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(securityProperty.getOpenApi()).permitAll()
                // 其他的需要登录后才能访问
                .anyRequest().authenticated()
                .and()
                // 验证码验证类
                .addFilterBefore(securityCaptchaSupport, UsernamePasswordAuthenticationFilter.class)
                .httpBasic()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .and()
                .formLogin()
                // 登录页面
                .loginPage("/login")
                // 登录接口
                .loginProcessingUrl("/login")
                // 配置登录成功自定义处理类
                .successHandler(securityAccessSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(securityAccessFailureHandler)
                .and()
                .logout()
                .addLogoutHandler(securityLogoutHandler)
                // 退出登录删除 cookie缓存
                .deleteCookies("JSESSIONID")
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(securityAccessLogoutHandler)
                .and()
                .exceptionHandling()
                // 配置没有权限自定义处理类
                .accessDeniedHandler(securityAccessDeniedHandler)
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("rememberme-token")
                .authenticationSuccessHandler(secureRememberMeHandler)
                .tokenRepository(securityUserTokenService)
                .key(securityProperty.getRememberKey())
                .and()
                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                // 在需要使用到session时才创建session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // 同时登陆多个只保留一个
                .maximumSessions(securityProperty.getMaximum())
                .maxSessionsPreventsLogin(false)
                // 踢出用户操作
                .expiredSessionStrategy(securityExpiredSessionHandler)
                // 用于统计在线
                .sessionRegistry(sessionRegistry);

        // 取消跨站请求伪造防护
        http.csrf().disable();
        // 防止iframe 造成跨域
        http.headers().frameOptions().disable();
    }
}
