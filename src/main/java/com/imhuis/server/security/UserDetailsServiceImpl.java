package com.imhuis.server.security;

import com.imhuis.server.common.exception.CustomizeException;
import com.imhuis.server.domain.securitybo.SecurityUser;
import com.imhuis.server.service.UserRoleService;
import com.imhuis.server.service.UserService;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserService userService;

    private UserRoleService userRoleService;

    public UserDetailsServiceImpl(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("loadUserByUsername -- userDetail:{}", s);
        // 邮箱
        if (new EmailValidator().isValid(s, null)){
            return userService.findUserByEmail(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + s + " was not found"));
        }
        // 手机号
        if (Character.isDigit(s.charAt(0))){
            return userService.findUserByPhone(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with phone " + s + " was not found"));
        }
        // 用户名
        return userService.findUserByUserName(s)
                .map(user -> createSpringSecurityUser(s, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + s + " was not found"));
    }

    private User createSpringSecurityUser(String login, com.imhuis.server.domain.security.User user){
        String userId = Optional.ofNullable(user.getUserId())
                .orElseThrow(() -> new CustomizeException("uid undefined"));
        String phone = Optional.ofNullable(user.getPhone()).orElseGet(() -> "undefined");
        String email = Optional.ofNullable(user.getEmail()).orElseGet(() -> "undefined");
        Set<String> userRolesStringList = userRoleService.getUserRolesString(user.getId());
        String authorityString = userRolesStringList.stream().collect(Collectors.joining(","));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
        log.info("find user [{}]", login);
        log.info("user info \n userid:{}", user.getUserId());
        return new SecurityUser(user.getUserName(), user.getPassword(), grantedAuthorities,
                userId, phone, email);
    }

}
