package com.shop_hub.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "organizations")
@Builder
public class Organization extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "logo")
    private String logo;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "terms_url")
    private String termsUrl;

    @Column(name = "privacy_url")
    private String privacyUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Admin createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Admin updatedBy;

    @Column(name = "status")
    private Integer status;

    public static final int DELETED = 0;
    public static final int ACTIVATED = 3;

}
