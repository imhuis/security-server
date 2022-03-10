package com.imhuis.server.domain;

import com.imhuis.server.common.enums.State;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Entity
@Table(name = "s_role")
public class Role extends BaseEntity implements Serializable {

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
