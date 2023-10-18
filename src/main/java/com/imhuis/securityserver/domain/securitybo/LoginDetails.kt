package com.imhuis.securityserver.domain.securitybo

import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
class LoginDetails : Serializable {

    var uid: String? = null
    var username: String? = null
    var phone: String? = null
    var email: String? = null
}