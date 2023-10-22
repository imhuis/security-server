package com.imhuis.securityserver.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 验证码校验失败异常
 */
public class CaptchaValidateException extends AuthenticationException {

    public CaptchaValidateException(String message) {
        super(message);
    }
}
