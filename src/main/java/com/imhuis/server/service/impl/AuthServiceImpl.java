package com.imhuis.server.service.impl;

import com.imhuis.server.domain.securitybo.SecurityUser;
import com.imhuis.server.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public SecurityUser queryByUid(String uid) {
        return null;
    }
}
