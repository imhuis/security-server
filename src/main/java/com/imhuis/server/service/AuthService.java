package com.imhuis.server.service;

import com.imhuis.server.security.bo.SecurityUser;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface AuthService {

    SecurityUser queryByUid(String uid);
}
