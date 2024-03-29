package com.imhuis.securityserver.domain

import jakarta.persistence.*
import java.io.Serializable

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: base Entity
 */
@MappedSuperclass
open class BaseEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "createTime", column = Column(name = "create_time")),
        AttributeOverride(name = "createBy", column = Column(name = "create_by")),
        AttributeOverride(name = "updateTime", column = Column(name = "update_time"))
    )
    open var auditMetaData: AuditMetaData? = null
}
