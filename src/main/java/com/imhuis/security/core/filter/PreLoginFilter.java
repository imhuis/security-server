package com.imhuis.security.core.filter;

import com.imhuis.security.core.security.enums.LoginTypeEnum;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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

    private static final String LOGIN_TYPE_KEY = "login_type";

    private RequestMatcher requestMatcher;

    private static Map<LoginTypeEnum,LoginProcessor> loginProcessorMap = new ConcurrentHashMap();


    static {
        loginProcessorMap.put(LoginTypeEnum.FORM, defaultLoginProcessor());
        loginProcessorMap.put(LoginTypeEnum.JSON, defaultLoginProcessor());
        loginProcessorMap.put(LoginTypeEnum.CAPTCHA, defaultLoginProcessor());
    }

    public PreLoginFilter(String loginProcessingUrl) {
        this.requestMatcher = new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        if (requestMatcher.matches((HttpServletRequest) request)) {
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
