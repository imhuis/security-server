package com.imhuis.server.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Entity
@Table(name = "s_user_role")
public class UserRole implements Serializable {

    @EmbeddedId
    private UserRoleKey userRoleKey;

    @Column(name = "uid")
    private String uid;
}
