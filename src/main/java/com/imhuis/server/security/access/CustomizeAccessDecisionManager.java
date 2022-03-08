package com.imhuis.server.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author: imhuis
 * @date: 2022/3/8
 * @description:
 */
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }

        Iterator<ConfigAttribute> configAttribute = configAttributes.iterator();
        while (configAttribute.hasNext()){
            ConfigAttribute c = configAttribute.next();
            String needRole = c.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority ga : authorities) {
                if (needRole.trim().equals(ga.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
