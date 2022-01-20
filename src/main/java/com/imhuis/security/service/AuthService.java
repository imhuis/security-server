package com.imhuis.security.service;

import com.imhuis.security.core.security.bo.SecurityUser;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface AuthService {

    SecurityUser queryByUid(String uid);
}
