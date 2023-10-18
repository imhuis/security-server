package com.imhuis.securityserver.security.filter;

import com.imhuis.securityserver.security.token.TokenAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: filter request with token
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected String getHeaderPrefix() {
        return "Token ";
    }

    @Override
    protected AbstractAuthenticationToken buildAuthentication(String header) {
        String[] tokens = header.split(" ");
        return new TokenAuthenticationToken(tokens[1]);
    }
}