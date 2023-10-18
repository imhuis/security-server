package com.imhuis.securityserver.security.filter;

import com.imhuis.securityserver.common.enums.LoginTypeEnum;
import com.imhuis.securityserver.common.enums.LoginTypeEnum;
import jakarta.servlet.ServletRequest;

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
