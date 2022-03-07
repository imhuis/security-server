package com.imhuis.server.core.filter;

import com.imhuis.server.common.base.ResponseResult;
import com.imhuis.server.common.base.ResponseUtil;
import com.imhuis.server.common.enums.ResponseCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.imhuis.server.common.constant.SecurityConstants.TOKEN_HEADER_NAME;


/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: filter request abstract
 */
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AbstractAuthenticationFilter.class);

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private AuthenticationManager authenticationManager;

    public AbstractAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.debug("TokenAuthenticationFilter do.");
        final String header = request.getHeader(TOKEN_HEADER_NAME);

        if (header == null || !header.startsWith(getHeaderPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        AbstractAuthenticationToken authRequest = buildAuthentication(header);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        final Authentication authResult;
        try {
            authResult = authenticationManager.authenticate(authRequest);
        } catch (AuthenticationException failed) {
            String errorMessage = failed.getMessage();
            SecurityContextHolder.clearContext();
            //response exception
//            ResponseTools.responseString(response, errorMessage);
            ResponseResult result = new ResponseResult(ResponseCodeEnum.AUTH_EXCEPTION);
            result.setData(errorMessage);
            ResponseUtil.out(response, result);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

        chain.doFilter(request, response);
    }

    protected abstract String getHeaderPrefix();

    protected abstract AbstractAuthenticationToken buildAuthentication(String header);
}