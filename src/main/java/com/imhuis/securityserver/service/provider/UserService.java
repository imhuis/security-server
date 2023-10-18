package com.imhuis.securityserver.service.provider;

import com.imhuis.securityserver.domain.security.User;

/**
 * @author: imhuis
 * @date: 2022/4/24
 * @description:
 */
public interface UserService {

    User loadUserByToken(String token);

}
