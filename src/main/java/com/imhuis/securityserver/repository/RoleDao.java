package com.imhuis.securityserver.repository;

import com.imhuis.securityserver.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description: RoleDao interface
 */
public interface RoleDao extends JpaRepository<Role, Long> {
}
