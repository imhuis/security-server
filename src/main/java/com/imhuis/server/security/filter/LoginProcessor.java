package com.imhuis.server.security.filter;

import com.imhuis.server.common.enums.LoginTypeEnum;

import javax.servlet.ServletRequest;

/**
 * @author: imhuis
 * @date: 2022/3/3
 * @description:
 */
public interface LoginProcessor {

    LoginTypeEnum getLoginType();

    String obtainUsername(ServletRequest request);

    String obtainPassword(ServletRequest request);
}
