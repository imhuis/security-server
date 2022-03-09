package com.imhuis.server.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Embeddable

/**
 * @author: imhuis
 * @date: 2022/3/5
 * @description: audit meta(审计信息)
 */
@Embeddable
class AuditMetaData {

    @CreatedDate
    var createTime: LocalDateTime? = null

    @CreatedBy
    var createBy: String? = null

    @LastModifiedDate
    var updateTime: LocalDateTime? = null
}