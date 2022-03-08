package com.imhuis.server.common.exception;

import javax.naming.AuthenticationException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 用户未激活异常
 */
public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
