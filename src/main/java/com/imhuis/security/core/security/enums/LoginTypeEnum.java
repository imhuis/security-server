package com.imhuis.security.core.security.enums;

/**
 * @author: imhuis
 * @date: 2022/1/15
 * @description:
 */
public enum LoginTypeEnum {

    FORM("form", 1) {
        @Override
        Integer getCode() {
            return 1;
        }
    },
    JSON("json", 2) {
        @Override
        Integer getCode() {
            return 2;
        }
    },
    CAPTCHA("captcha", 3) {
        @Override
        Integer getCode() {
            return 3;
        }
    };

    private String loginType;
    private Integer code;

    LoginTypeEnum(final String loginType, final Integer code) {
        this.loginType = loginType;
        this.code = code;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    abstract Integer getCode();
}
