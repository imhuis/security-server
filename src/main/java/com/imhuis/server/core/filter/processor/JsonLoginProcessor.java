package com.imhuis.server.core.filter.processor;

import com.imhuis.server.core.filter.LoginProcessor;
import com.imhuis.server.security.enums.LoginTypeEnum;

import javax.servlet.ServletRequest;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description:
 */
public class JsonLoginProcessor implements LoginProcessor {

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.JSON;
    }

    @Override
    public String obtainUsername(ServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    @Override
    public String obtainPassword(ServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    }
}
