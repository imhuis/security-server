package com.imhui.security.service;

/**
 * @author: imhuis
 * @date: 2022/1/15
 * @description:
 */
public interface CaptchaCacheStorage {

    String put(String phone);

    String get(String phone);

    void  expire(String phone);
}
