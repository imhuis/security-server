package com.imhuis.server.security;

import com.imhuis.server.security.filter.TokenAuthenticationFilter;
import com.imhuis.server.security.filter.UsernamePasswordJsonAuthenticationFilter;
import com.imhuis.server.security.token.TokenAuthenticationProvider;
import com.imhuis.server.handler.CustomizeAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Instant;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description: security config
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(httpFirewall());
    }

    @Bean
    @Order(1)
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .authenticationManager()
                .authenticationProvider(authenticationProvider())
                .authenticationProvider(tokenAuthenticationProvider())
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login").permitAll()
                        .antMatchers("/public/**").permitAll()
                        .antMatchers(
                                "/*.html",
                                "/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js",
                                "/h2-console/**").permitAll()
                        .antMatchers("/actuator/**")
                        .access("hasIpAddress('127.0.0.0/8') or hasIpAddress('192.168.0.0/16')")
                        .requestMatchers(EndpointRequest.to(MetricsEndpoint.class)).hasIpAddress("192.168.0.0/16")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler))
                .logout(LogoutConfigurer::permitAll)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1))
                )
                .headers(headersCustomizer -> headersCustomizer
                        .addHeaderWriter((request, response) -> response.setHeader("timestamp", Instant.now().toString())))
//                .expiredSessionStrategy()
        .apply(new DefaultSecurityDsl());
        return http.build();
    }

    class DefaultSecurityDsl extends AbstractHttpConfigurer<DefaultSecurityDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
//            http.addFilterBefore(new PreLoginFilter("/login"), UsernamePasswordAuthenticationFilter.class);
//            http.addFilterBefore(usernamePasswordJsonAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        }
    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder();
//        return null;
//    }

//    @Bean
//    public PreLoginFilter preLoginFilter() {
//        return new PreLoginFilter("/login");
//    }

    public UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter =
                new UsernamePasswordJsonAuthenticationFilter(authenticationManager, true);
        usernamePasswordJsonAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        usernamePasswordJsonAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return usernamePasswordJsonAuthenticationFilter;
    }

//    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

//    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("GET", "PATCH", "POST", "DELETE", "HEAD"));
        configuration.setExposedHeaders(List.of("X-Auth-Token"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public HttpFirewall httpFirewall() {
        DefaultHttpFirewall httpFirewall = new DefaultHttpFirewall();
        httpFirewall.setAllowUrlEncodedSlash(true);
        return httpFirewall;
    }

}