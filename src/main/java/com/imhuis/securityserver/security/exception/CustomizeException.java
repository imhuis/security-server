package com.imhuis.securityserver.security.exception;

/**
 * @author: zyixh
 * @date:   2020/1/27
 * @description: 自定义异常
 */
public class CustomizeException extends RuntimeException {

    private CustomizeException() {
    }

    public CustomizeException(String message) {
        super(message);
    }
}
