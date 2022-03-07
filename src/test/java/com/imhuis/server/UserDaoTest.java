package com.imhuis.server;

import com.imhuis.server.domain.User;
import com.imhuis.server.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void initUser(){
        User newUser = new User();
        newUser.setUserName("yangz");
        newUser.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        newUser.setPassword(passwordEncoder.encode("123456"));
        userDao.save(newUser);
    }

    @Test
    public void encode(){
        System.out.println(passwordEncoder.encode("7e3be299-d3b8-49a3-9f79-53d57b2b9632"));
    }
}
