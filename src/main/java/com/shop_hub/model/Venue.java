package com.shop_hub.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "venues")
@Builder
public class Venue extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User updatedBy;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> admins;

    public static final int DELETED = 0;
    public static final int ACTIVATED = 1;

}
