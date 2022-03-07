package com.imhuis.server.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Email

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