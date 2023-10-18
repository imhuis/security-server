package com.imhuis.securityserver.service;

import com.imhuis.securityserver.domain.security.User;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface UserService {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByUserName(String userName);

}
