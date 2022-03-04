package com.imhuis.security.config;

import com.imhuis.security.core.filter.PreLoginFilter;
import com.imhuis.security.core.filter.TokenAuthenticationFilter;
import com.imhuis.security.core.filter.UsernamePasswordJsonAuthenticationFilter;
import com.imhuis.security.core.security.token.TokenAuthenticationProvider;
import com.imhuis.security.handler.CustomizeAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description:
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
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder builder) throws Exception {
        AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver = request -> builder.getObject();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry
                .and()
                .csrf(CsrfConfigurer::disable)
//                .disable()
                .cors()
                .and()
                .authorizeRequests()
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

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
//                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement(sessionManagement -> sessionManagement.maximumSessions(5))
//                .expiredSessionStrategy()
        ;
        // 自定义Token认证
        http.addFilterBefore(new TokenAuthenticationFilter(builder.getObject()), BasicAuthenticationFilter.class);
        // 自定义json登陆必须要在bean中声明
        http.addFilterBefore(preLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAt(usernamePasswordJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return registry.and().build();
    }

    @Bean
    public PreLoginFilter preLoginFilter() {
        return new PreLoginFilter("/login");
    }

//    @Bean
//    public UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter() throws Exception {
//        UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter =
//                new UsernamePasswordJsonAuthenticationFilter(authenticationManagerBean(), true);
//        usernamePasswordJsonAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        usernamePasswordJsonAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
//        return usernamePasswordJsonAuthenticationFilter;
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Bean
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
