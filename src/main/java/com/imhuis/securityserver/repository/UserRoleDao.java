package com.imhuis.securityserver.repository;

import com.imhuis.securityserver.domain.security.UserRole;
import com.imhuis.securityserver.domain.security.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description: UserRoleDao interface
 */
public interface UserRoleDao extends JpaRepository<UserRole, UserRoleKey> {

//    List<String> queryRoleNames();

}
