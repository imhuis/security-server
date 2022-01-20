package com.imhuis.security.core.security.token;

import com.imhuis.security.core.security.bo.SecurityUser;
import com.imhuis.security.service.AuthService;
import com.imhuis.security.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: token鉴权校验器
 */
@Log4j2
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Authentication Token
        String token = authentication.getName();
        log.info("Authentication Token:{} -- {}", LocalDateTime.now(), token);
        String uid = null;
        try {
            uid = tokenService.getSecurityUserIdFromToken(token);
            tokenService.renewalToken(token);
        }
//        catch (NodeMgrException e) {
//            throw e;
//        }
        catch (Exception e) {
            throw new BadCredentialsException("db");
        }
        if (null == uid) {
            throw new CredentialsExpiredException("Invalid token");
        }
        AbstractAuthenticationToken authResult = buildAuthentication(uid);
        authResult.setDetails(authentication.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Collection<SimpleGrantedAuthority> buildAuthorities(SecurityUser user) {
        final Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//        List<String> authorities = getUserAuthorities(user);
//        for (String authority : authorities) {
//            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority));
//        }
        return simpleGrantedAuthorities;
    }

    private AbstractAuthenticationToken buildAuthentication(String uid) {
        SecurityUser securityUser = authService.queryByUid(uid);
        log.debug("securityUser > uid: {}", securityUser.getUid());
//        return new TokenAuthenticationToken(securityUser.getAccount(), buildAuthorities(tbAccountInfo));
        return new TokenAuthenticationToken(securityUser.getUid());
    }

//    private List<String> getUserAuthorities(SecurityUser user) {
//        return Arrays.asList("ROLE_" + user.getRoleName());
//    }

}