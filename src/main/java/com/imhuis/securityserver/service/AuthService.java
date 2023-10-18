package com.imhuis.securityserver.service;

import com.imhuis.securityserver.domain.securitybo.SecurityUser;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface AuthService {

    SecurityUser queryByUid(String uid);
}
