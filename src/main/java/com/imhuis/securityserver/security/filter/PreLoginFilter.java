package com.imhuis.securityserver.security.filter;

import com.imhuis.securityserver.security.filter.processor.CaptchaLoginProcessor;
import com.imhuis.securityserver.security.filter.processor.JsonLoginProcessor;
import com.imhuis.securityserver.common.enums.LoginTypeEnum;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

/**
 * @author: imhuis
 * @date: 2022/3/3
 * @description: 预登录过滤器
 */
public class PreLoginFilter extends GenericFilter {

    private static final Logger log = LoggerFactory.getLogger(PreLoginFilter.class);

    private static final String LOGIN_TYPE_KEY = "login_type";

    private RequestMatcher requestMatcher;

    private static Map<LoginTypeEnum,LoginProcessor> loginProcessorMap = new ConcurrentHashMap();


    static {
        loginProcessorMap.put(LoginTypeEnum.FORM, defaultLoginProcessor());
        loginProcessorMap.put(LoginTypeEnum.JSON, new JsonLoginProcessor());
        loginProcessorMap.put(LoginTypeEnum.CAPTCHA, new CaptchaLoginProcessor());
    }

    public PreLoginFilter(String loginProcessingUrl) {
        this.requestMatcher = new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        if (requestMatcher.matches((HttpServletRequest) request)) {
            log.debug("PreLoginFilter do.");
            LoginTypeEnum loginTypeEnum = getType(request);
            LoginProcessor processor = loginProcessorMap.get(loginTypeEnum);
            String username = processor.obtainUsername(request);
            String password = processor.obtainPassword(request);
            requestWrapper.setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, username);
            requestWrapper.setAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY, password);
        }
        chain.doFilter(requestWrapper, response);

    }

    private LoginTypeEnum getType(ServletRequest request) {
        String loginType = request.getParameter(LOGIN_TYPE_KEY);
        int i = Integer.valueOf(loginType);
        LoginTypeEnum[] loginTypeEnums = LoginTypeEnum.values();
        return loginTypeEnums[i];
    }

    private static LoginProcessor defaultLoginProcessor() {
        return new LoginProcessor() {

            @Override
            public LoginTypeEnum getLoginType() {
                return LoginTypeEnum.FORM;
            }

            @Override
            public String obtainUsername(ServletRequest request) {
                return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
            }

            @Override
            public String obtainPassword(ServletRequest request) {
                return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
            }
        };
    }
}
