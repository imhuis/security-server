package com.imhuis.server.service;

import com.imhuis.server.domain.UserRole;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
public interface UserRoleService {

    List<String> getUserRolesString(Long userId);

    List<UserRole> getUserRoles();
}
