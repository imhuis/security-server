package com.imhuis.server.service;

import com.imhuis.server.domain.User;

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
