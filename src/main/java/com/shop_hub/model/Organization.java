package com.shop_hub.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

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

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "terms_url")
    private String termsUrl;

    @Column(name = "privacy_url")
    private String privacyUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User updatedBy;

    @Column(name = "status")
    private Boolean status;

    public static final boolean DELETED = false;
    public static final boolean ACTIVATED = true;

}
