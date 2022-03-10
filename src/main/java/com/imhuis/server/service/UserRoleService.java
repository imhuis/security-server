package com.imhuis.server.service;

import com.imhuis.server.domain.security.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface UserRoleService {

    Set<String> getUserRolesString(Long userId);

    List<UserRole> getUserRoles();
}
