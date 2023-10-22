package com.imhuis.securityserver.web.authentication.controller;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2023/10/18
 * @description:
 */
public class LoginRequest implements Serializable {

    private String username;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
