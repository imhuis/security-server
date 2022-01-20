package com.imhuis.security.repository;

import com.imhuis.security.domain.User;
import com.imhuis.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByEmail(String email);

}
