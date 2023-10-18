package com.imhuis.securityserver.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: token过期异常
 */
public class TokenExpiredException extends AuthenticationException {

    public TokenExpiredException(String msg) {
        super(msg);
    }

    public TokenExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
