package com.imhui.security.common.enums;

/**
 * @author: imhuis
 * @date: 2022/1/15
 * @description:
 */
public enum LoginTypeEnum {

    FORM {
        @Override
        Integer getCode() {
            return 1;
        }
    },
    JSON {
        @Override
        Integer getCode() {
            return 2;
        }
    },
    CAPTCHA {
        @Override
        Integer getCode() {
            return 3;
        }
    };

    abstract Integer getCode();
}
