package com.imhuis.security.core.filter;

import com.imhuis.security.core.security.enums.LoginTypeEnum;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
