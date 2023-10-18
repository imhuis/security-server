package com.imhuis.securityserver.service.impl;

import com.imhuis.securityserver.domain.securitybo.SecurityUser;
import com.imhuis.securityserver.service.AuthService;
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
