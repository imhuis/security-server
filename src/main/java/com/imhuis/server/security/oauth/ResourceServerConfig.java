package com.imhuis.server.security.oauth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author: imhuis
 * @date: 2021/9/6
 * @description:
 */
//@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        super.configure(resources);
        resources
                .resourceId("*")
                .stateless(true)
//                .tokenStore(tokenStore)
//                .tokenServices(tokenService)
//                .authenticationEntryPoint(oauthTokenExceptionEntryPoint)
//                .accessDeniedHandler(oauthAccessDeniedHandler)
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/app/**")

                .and()
                .authorizeRequests()
                .antMatchers("/app/oauth/**").permitAll()
//                .anyRequest().authenticated()
                .antMatchers("/app/**").authenticated()

                // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .csrf()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(oauthTokenExceptionEntryPoint)
//                .accessDeniedHandler(oauthAccessDeniedHandler)
        ;
    }
}
