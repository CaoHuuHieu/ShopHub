package com.shop_hub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity{

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_code")
    private String roleCode;

}
