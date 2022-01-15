package com.imhui.security.filter;

import com.imhui.security.common.util.JsonTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE.equals(request.getContentType())) {
            try (InputStream is = request.getInputStream()) {
                log.debug("is a json request.");
                Map<String, String> authenticationMap = JsonTools.streamToObj(is, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationMap.get(usernameField), authenticationMap.get(passwordField));
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

}
