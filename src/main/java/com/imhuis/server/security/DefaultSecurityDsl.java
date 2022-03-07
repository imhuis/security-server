package com.imhuis.server.security;

import com.imhuis.server.core.filter.TokenAuthenticationFilter;
import com.imhuis.server.core.filter.UsernamePasswordJsonAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author: imhuis
 * @date: 2022/3/7
 * @description:
 */
public class DefaultSecurityDsl extends AbstractHttpConfigurer<DefaultSecurityDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
//            http.addFilterBefore(new PreLoginFilter("/login"), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new UsernamePasswordJsonAuthenticationFilter(authenticationManager, true), UsernamePasswordAuthenticationFilter.class);
    }

    public static DefaultSecurityDsl defaultSecurityDsl() {
        return new DefaultSecurityDsl();
    }

}
