package com.imhuis.securityserver.security;

import com.imhuis.securityserver.security.filter.PreLoginFilter;
import com.imhuis.securityserver.security.handler.CustomizeAccessDeniedHandler;
import com.imhuis.securityserver.security.access.CustomAuthorizationManager;
import com.imhuis.securityserver.security.access.CustomizeFilterSecurityInterceptor;
import com.imhuis.securityserver.security.filter.PreLoginFilter;
import com.imhuis.securityserver.security.filter.TokenAuthenticationFilter;
import com.imhuis.securityserver.security.filter.UsernamePasswordJsonAuthenticationFilter;
import com.imhuis.securityserver.security.token.TokenAuthenticationProvider;
import com.imhuis.securityserver.security.handler.CustomizeAccessDeniedHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.time.Instant;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description: security config
 */
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
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

//    @Autowired
//    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(httpFirewall());
    }

    /**
     * SecurityFilterChain
     *
     * @param http
     * @param access
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> access) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .authenticationProvider(daoAuthenticationProvider())
//                .authenticationProvider(tokenAuthenticationProvider())
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(EndpointRequest.to(MetricsEndpoint.class)).permitAll()
                        .anyRequest().access(access)
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/login")
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                        .permitAll()
                )

//                .logout(LogoutConfigurer::permitAll)
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
                .addFilterBefore(new TokenAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
                .addFilterBefore(new PreLoginFilter("/login"), UsernamePasswordAuthenticationFilter.class);
        ;
        return http.build();
    }

    @Bean
    AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthorizationManager(HandlerMappingIntrospector introspector) {
        RequestMatcher permitAll = new AndRequestMatcher(
                new AntPathRequestMatcher("/**/*.html"),
                new AntPathRequestMatcher("/**/*.css"),
                new AntPathRequestMatcher("/**/*.js"),
                new AntPathRequestMatcher("/favicon.ico"),
                new AntPathRequestMatcher("/*.html"),
                new AntPathRequestMatcher("/public/**"));
        RequestMatcher admin = new AndRequestMatcher(
                new MvcRequestMatcher(introspector, "/sys/**"),
                new MvcRequestMatcher(introspector, "/admin/**"));
        RequestMatcher api = new MvcRequestMatcher(introspector, "/api/**");
        RequestMatcher any = AnyRequestMatcher.INSTANCE;
        AuthorizationManager<HttpServletRequest> authz = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(permitAll, (authentication, obj) -> new AuthorizationDecision(true))
                .add(admin, AuthorityAuthorizationManager.hasRole("admin"))
                .add(api, AuthorityAuthorizationManager.hasRole("api"))
                .add(any, new AuthenticatedAuthorizationManager())
                .build();
        return (authentication, context) -> authz.check(authentication, context.getRequest());
    }


//    @Bean
//    public CustomizeFilterSecurityInterceptor customizeFilterSecurityInterceptor() {
//        CustomizeFilterSecurityInterceptor filterSecurityInterceptor = new CustomizeFilterSecurityInterceptor(filterInvocationSecurityMetadataSource);
//        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
//        return filterSecurityInterceptor;
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder();
//        return null;
//    }

    public UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter =
                new UsernamePasswordJsonAuthenticationFilter(authenticationManager, true);
        usernamePasswordJsonAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        usernamePasswordJsonAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return usernamePasswordJsonAuthenticationFilter;
    }

    // provider


    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

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

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

}
