package com.imhuis.security.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * @author: imhuis
 * @date: 2022/3/5
 * @description:
 */
@Embeddable
class AuditMetaData {

    @CreatedDate
    var createTime: LocalDateTime? = null

    //    @CreatedBy
    //    private Long createBy;
    @LastModifiedDate
    var updateTime: LocalDateTime? = null
}