package com.imhuis.securityserver.service;

import com.imhuis.securityserver.domain.securitybo.LoginDetails;
import com.imhuis.securityserver.domain.securitybo.TokenInfo;
import com.imhuis.securityserver.domain.securitybo.SecurityUser;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
public interface TokenService {

    /**
     * 生成token
     * @param loginDetails
     * @return
     */
    TokenInfo createToken(LoginDetails loginDetails);

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
