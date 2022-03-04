package com.imhuis.security.core.security.bo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
class SecurityUser : User, Serializable {

    var uid: String
    var phone: String
    var email: String

    constructor(
        username: String?,
        password: String?,
        authorities: Collection<GrantedAuthority?>?,
        uid: String,
        phone: String,
        email: String
    ) : super(username, password, authorities) {
        this.uid = uid
        this.phone = phone
        this.email = email
    }

    constructor(
        username: String?,
        password: String?,
        enabled: Boolean,
        accountNonExpired: Boolean,
        credentialsNonExpired: Boolean,
        accountNonLocked: Boolean,
        authorities: Collection<GrantedAuthority?>?,
        uid: String,
        phone: String,
        email: String
    ) : super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities) {
        this.uid = uid
        this.phone = phone
        this.email = email
    }

    companion object {
        private const val serialVersionUID = -1L
    }
}