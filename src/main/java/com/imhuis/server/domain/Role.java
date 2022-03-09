package com.imhuis.server.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: imhuis
 * @date: 2022/3/9
 * @description:
 */
@Entity
@Table(name = "s_role")
public class Role extends BaseEntity implements Serializable {

    private String roleId;
    private String roleName;

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
}
