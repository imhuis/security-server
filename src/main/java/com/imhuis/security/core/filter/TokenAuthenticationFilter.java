package com.imhuis.security.core.filter;

import com.imhuis.security.core.security.token.TokenAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: filter request with token
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

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