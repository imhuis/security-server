package com.imhuis.server.service.impl;

import com.imhuis.server.domain.UserRole;
import com.imhuis.server.domain.UserRoleKey;
import com.imhuis.server.repository.RoleDao;
import com.imhuis.server.repository.UserRoleDao;
import com.imhuis.server.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

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
    public List<String> getUserRolesString(Long userId) {
        UserRole userRole = new UserRole();
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId(userId);
        userRole.setUserRoleKey(userRoleKey);
        List<UserRole> userRoleList = userRoleDao.findAll(Example.of(userRole));
        List<String> collect = userRoleList.stream()
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
                .collect(toList());
        return collect;
    }

    @Override
    public List<UserRole> getUserRoles() {
        return null;
    }
}
