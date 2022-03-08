package com.imhuis.server.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @author: imhuis
 * @date: 2022/3/8
 * @description: 身份认证事件监听器
 * {@link org.springframework.security.authentication.DefaultAuthenticationEventPublisher}
 */
@Component
public class AuthenticationEvents {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationEvents.class);

    @EventListener
    public void onLoginFailure(AuthenticationFailureBadCredentialsEvent event) {
        log.info("login failure: {}", event);
    }
}
