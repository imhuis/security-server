package com.imhuis.server.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author: imhuis
 * @date: 2022/3/8
 * @description:
 */
public class SecurityUtil {

    public static final String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "";
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
