package com.imhuis.security.core.security;

import com.imhuis.security.common.exception.CustomizeException;
import com.imhuis.security.core.security.bo.SecurityUser;
import com.imhuis.security.repository.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("loadUserByUsername -- userDetail:{}", s);
        // 邮箱
        if (new EmailValidator().isValid(s, null)){
            return userDao.findUserByEmail(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + s + " was not found"));
        }
        // 手机号
        if (Character.isDigit(s.charAt(0))){
            return userDao.findUserByPhone(s)
                    .map(user -> createSpringSecurityUser(s, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with phone " + s + " was not found"));
        }
        // 用户名
        return userDao.findUserByUserName(s)
                .map(user -> createSpringSecurityUser(s, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + s + " was not found"));
    }

    private User createSpringSecurityUser(String login, com.imhuis.security.domain.User user){
        String userId = Optional.ofNullable(user.getUserId())
                .orElseThrow(() -> new CustomizeException("uid undefined"));
        String phone = Optional.ofNullable(user.getPhone()).orElseGet(() -> "undefined");
        String email = Optional.ofNullable(user.getEmail()).orElseGet(() -> "undefined");
        StringUtils.isBlank(userId);
//        if (false){
//            throw new UserNotActivatedException("");
//        }
        List<String> authorityString = null;
//        AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

//        List<GrantedAuthority> grantedAuthorities = authorityString
//                .stream().map(authority -> new SimpleGrantedAuthority(authority))
//                .collect(Collectors.toList());
        logger.info("find user [{}]", login);
        logger.info("user info \n userid:{}", user.getUserId());
        return new SecurityUser(user.getUserName(), user.getPassword(), Collections.emptyList(),
                user.getUserId(), phone, email);
    }

}
