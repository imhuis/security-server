package com.imhuis.security.core.security.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class SecurityUser extends User implements Serializable {

    private static final long serialVersionUID = -1L;

    private String uid;
    private String phone;
    private String email;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String uid, String phone, String email) {
        super(username, password, authorities);
        this.uid = uid;
        this.phone = phone;
        this.email = email;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String uid, String phone, String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.uid = uid;
        this.phone = phone;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
