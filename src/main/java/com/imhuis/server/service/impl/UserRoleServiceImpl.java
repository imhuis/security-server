package com.imhuis.server.service.impl;

import com.imhuis.server.repository.UserRoleDao;
import com.imhuis.server.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleDao userRoleDao;

    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }


}
