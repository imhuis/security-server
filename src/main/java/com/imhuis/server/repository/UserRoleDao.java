package com.imhuis.server.repository;

import com.imhuis.server.domain.UserRole;
import com.imhuis.server.domain.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface UserRoleDao extends JpaRepository<UserRole, UserRoleKey> {

//    List<String> queryRoleNames();

}
