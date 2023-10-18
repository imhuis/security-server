package com.imhuis.securityserver.service.provider;

import com.imhuis.securityserver.domain.security.User;
import org.springframework.stereotype.Service;

/**
 * @author: imhuis
 * @date: 2022/4/24
 * @description:
 */
@Service("userServiceProvider")
public class UserServiceImpl implements UserService {

    @Override
    public User loadUserByToken(String token) {
        return null;
    }
}
