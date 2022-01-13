package com.imhui.security.service.impl;

import com.imhui.security.core.security.bo.SecurityUser;
import com.imhui.security.service.AuthService;
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
