package com.imhuis.server.repository;

import com.imhuis.server.domain.security.UserRole;
import com.imhuis.server.domain.security.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface UserRoleDao extends JpaRepository<UserRole, UserRoleKey> {

//    List<String> queryRoleNames();

}
