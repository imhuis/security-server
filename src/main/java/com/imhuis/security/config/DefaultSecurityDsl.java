package com.imhuis.security.config;

import com.imhuis.security.core.filter.PreLoginFilter;
import com.imhuis.security.core.filter.TokenAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author: imhuis
 * @date: 2022/3/5
 * @description:
 */
public class DefaultSecurityDsl extends AbstractHttpConfigurer<DefaultSecurityDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
        // 自定义json登陆必须要在bean中声明
        http.addFilterBefore(new PreLoginFilter("/login"), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAt(usernamePasswordJsonAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

}
