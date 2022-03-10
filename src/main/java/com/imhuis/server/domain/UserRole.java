package com.imhuis.server.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "create_by")
    private String createBy;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public UserRoleKey getUserRoleKey() {
        return userRoleKey;
    }

    public void setUserRoleKey(UserRoleKey userRoleKey) {
        this.userRoleKey = userRoleKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
