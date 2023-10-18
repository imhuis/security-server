package com.imhuis.securityserver.service.impl;

import com.imhuis.securityserver.domain.security.UserRole;
import com.imhuis.securityserver.domain.security.UserRoleKey;
import com.imhuis.securityserver.repository.RoleDao;
import com.imhuis.securityserver.repository.UserRoleDao;
import com.imhuis.securityserver.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleDao userRoleDao;

    private RoleDao roleDao;

    public static final String ROLE_PREFIX = "ROLE_";

    public UserRoleServiceImpl(UserRoleDao userRoleDao, RoleDao roleDao) {
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
    }

    @Override
    public Set<String> getUserRolesString(Long userId) {
        UserRole userRole = new UserRole();
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId(userId);
        userRole.setUserRoleKey(userRoleKey);
        List<UserRole> userRoleList = userRoleDao.findAll(Example.of(userRole));
        Set<String> roleSets = userRoleList.stream()
                .map(UserRole::getUserRoleKey)
                .map(UserRoleKey::getRoleId)
                .map(roleId -> roleDao.findById(roleId).orElseGet(null).getRoleName())
                .filter(Objects::nonNull)
                .map(role -> {
                    if (StringUtils.startsWith(role, ROLE_PREFIX)) {
                        return role;
                    }else {
                        return ROLE_PREFIX + role;
                    }
                })
                .collect(toSet());
        return roleSets;
    }

    @Override
    public List<UserRole> getUserRoles() {
        return null;
    }
}
