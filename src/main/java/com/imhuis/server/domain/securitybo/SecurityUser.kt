package com.imhuis.server.domain.securitybo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 自定义User类，继承UserDetails
 */
class SecurityUser : User, Serializable {

    // uid - 用户id
    var uid: String
    // 手机号
    var phone: String
    // 邮箱
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