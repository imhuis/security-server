package com.imhuis.security.service.impl;

import com.imhuis.security.core.security.bo.SecurityUser;
import com.imhuis.security.service.AuthService;
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
