package com.imhuis.securityserver.security.Authorization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author: imhuis
 * @date: 2022/3/12
 * @description: FilterSecurityInterceptor is in the process of being replaced by AuthorizationFilter
 * @see: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#authorization-filter
 */
public class CustomAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    @Override
    public void verify(Supplier<Authentication> authentication, HttpServletRequest object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();

//        return authentication.get().isAuthenticated();
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
        return null;
    }
}
