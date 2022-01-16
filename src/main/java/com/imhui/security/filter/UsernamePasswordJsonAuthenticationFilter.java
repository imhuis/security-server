package com.imhui.security.filter;

import com.imhui.security.common.util.JsonTools;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
@Slf4j
public class UsernamePasswordJsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String usernameField = "username";
    private String passwordField = "password";
    private boolean postOnly = true;

    public UsernamePasswordJsonAuthenticationFilter(AuthenticationManager authenticationManager, boolean postOnly) {
        super(authenticationManager);
        this.postOnly = postOnly;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            log.info("request type:\n {}", request.getClass());
            ServletWebRequest servletWebRequest = new ServletWebRequest(request);
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(servletWebRequest.getRequest());
            try {
                log.debug("is a json request.");
                String str = new String(requestWrapper.getContentAsByteArray());
                Map<String, String> authenticationMap = JsonTools.stringToObj(str, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationMap.get(usernameField), authenticationMap.get(passwordField));
                log.info("get json value {},{}", authRequest.getPrincipal(), authRequest.getCredentials());
            } finally {
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }

}
