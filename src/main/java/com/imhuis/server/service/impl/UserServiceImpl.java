package com.imhuis.server.service.impl;

import com.imhuis.server.domain.security.User;
import com.imhuis.server.repository.UserDao;
import com.imhuis.server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userdao;

    public UserServiceImpl(UserDao userdao) {
        this.userdao = userdao;
    }


    @Override
    public Optional<User> findUserByEmail(String email) {
        return userdao.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByPhone(String phone) {
        return userdao.findUserByPhone(phone);
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return userdao.findUserByUserName(userName);
    }
}
