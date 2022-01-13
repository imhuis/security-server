package com.imhui.security.service;

import com.imhui.security.core.security.bo.SecurityUser;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface AuthService {

    SecurityUser queryByUid(String uid);
}
