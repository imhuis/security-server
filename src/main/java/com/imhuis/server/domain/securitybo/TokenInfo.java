package com.imhuis.server.domain.securitybo;

import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class TokenInfo implements Serializable {

    private String token;

    // ζζζΆι΄
    private long expires;

    public TokenInfo() {
    }

    public TokenInfo(String token, long expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
