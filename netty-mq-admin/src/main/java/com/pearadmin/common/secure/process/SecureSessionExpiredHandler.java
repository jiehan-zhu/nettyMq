package com.pearadmin.common.secure.process;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Security Session 过期 Handler
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class SecureSessionExpiredHandler implements SessionInformationExpiredStrategy {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/login?abnormalout=1");
    }
}
