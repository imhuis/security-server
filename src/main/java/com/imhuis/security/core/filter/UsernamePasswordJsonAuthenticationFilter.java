package com.imhuis.security.core.filter;

import com.imhuis.security.common.util.JsonTools;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
public class UsernamePasswordJsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(UsernamePasswordJsonAuthenticationFilter.class);

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
//            ServletWebRequest servletWebRequest = new ServletWebRequest(request);
            // className org.springframework.web.util.ContentCachingRequestWrapper
            ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
            // InputStream is = requestWrapper.getInputStream()
            try {
                String requestBody = new String(requestWrapper.getContentAsByteArray());
                log.info("login is a json request.");
//                String requestBody = IOUtils.toString(is, StandardCharsets.UTF_8);
                Map<String, String> authenticationMap = JsonTools.stringToObj(requestBody, Map.class);
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
