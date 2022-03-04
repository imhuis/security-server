package com.imhuis.security.core.filter.processor;

import com.imhuis.security.core.filter.LoginProcessor;
import com.imhuis.security.core.security.enums.LoginTypeEnum;

import javax.servlet.ServletRequest;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description:
 */
public class JsonLoginProcessor implements LoginProcessor {

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.JSON;
    }

    @Override
    public String obtainUsername(ServletRequest request) {
        return null;
    }

    @Override
    public String obtainPassword(ServletRequest request) {
        return null;
    }
}
