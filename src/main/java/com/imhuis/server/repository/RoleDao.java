package com.imhuis.server.repository;

import com.imhuis.server.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface RoleDao extends JpaRepository<Role, Long> {
}
