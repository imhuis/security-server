package com.imhuis.securityserver.security.filter;

import com.google.common.base.Strings;
import com.imhuis.securityserver.common.constant.SecurityConstants;
import com.imhuis.securityserver.domain.securitybo.TokenAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class MyTokenAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyTokenAuthenticationFilter.class);

//    public static final String TOKEN_HEADER = "Authorization";

    public MyTokenAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String requestTokenInServlet = request.getParameter(SecurityConstants.TOKEN_PARAMETER);
//        if (Strings.isNullOrEmpty(requestTokenInServlet)){
//
//        }
        // start Authentication
        try {
            logger.info("start token authentication");
            TokenAuthentication authRequest = this.convert(request);
            if (authRequest == null){
                chain.doFilter(request, response);
                return;
            }
            logger.info("[TokenAuthenticationFilter] request token: {}", authRequest.getCredentials());
            Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            this.onSuccessfulAuthentication(request, response, authResult);
        }catch (AuthenticationException e){
            SecurityContextHolder.clearContext();
            this.onUnsuccessfulAuthentication(request, response, e);
            if (this.isIgnoreFailure()) {
                chain.doFilter(request, response);
            } else {
                this.getAuthenticationEntryPoint().commence(request, response, e);
            }
            return;
        }

        chain.doFilter(request, response);
    }

    private TokenAuthentication convert(HttpServletRequest request){
        String headerTokenInServlet = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        String requestToken;
        if (!Strings.isNullOrEmpty(headerTokenInServlet)){
            requestToken = headerTokenInServlet;
        }else {
            String requestTokenInServlet = request.getParameter(SecurityConstants.TOKEN_PARAMETER);
            if (Strings.isNullOrEmpty(requestTokenInServlet)){
                return null;
            }else {
                requestToken = requestTokenInServlet;
            }
        }

        TokenAuthentication tokenAuthentication = new TokenAuthentication(requestToken.toLowerCase().trim());
        return tokenAuthentication;

    }
}
