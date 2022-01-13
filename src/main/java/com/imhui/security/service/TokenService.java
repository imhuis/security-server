package com.imhui.security.service;

import com.imhui.security.core.security.bo.SecurityUser;
import com.imhui.security.core.security.bo.TokenInfo;
import org.springframework.security.core.userdetails.User;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface TokenService {

    /**
     * 生成token
     * @param user
     * @return
     */
    TokenInfo createToken(SecurityUser user);

    /**
     * token续期
     * @param tokenName
     */
    void renewalToken(String tokenName);

    /**
     * token清除
     * @param token
     */
    void deleteToken(String token);

    /**
     * 根据token获取当前用户id
     * @param token
     * @return
     */
    String getSecurityUserIdFromToken(String token);

    SecurityUser getSecurityUserFromToken(String token);
}
