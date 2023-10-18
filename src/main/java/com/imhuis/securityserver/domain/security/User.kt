package com.imhuis.securityserver.domain.security

import com.imhuis.securityserver.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import java.io.Serializable


/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Entity
@Table(name = "s_user")
class User : BaseEntity(), Serializable {

    @Column(name = "uid", nullable = false)
    var userId: String? = null

    @Column(name = "username")
    var userName: String? = null

    @Column(name = "email")
    var email: @Email String? = null

    @Column(name = "phone")
    var phone: String? = null

    @Column(name = "pwd", nullable = false)
    var password: String? = null

    @Column(name = "salt_key")
    var salt: String? = null

    @Column(name = "nickname")
    var nickName: String? = null
}