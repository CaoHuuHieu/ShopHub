package com.shop_hub.dto.organization;

import com.shop_hub.dto.admin.AdminPlainDto;
import lombok.Data;

import java.time.Instant;


@Data
public class OrganizationDto {

    private Long id;

    private String name;

    private String code;

    private String logo;

    private String website;

    private String email;

    private String address;

    private String termsUrl;

    private String privacyUrl;

    private AdminPlainDto createdBy;

    private AdminPlainDto updatedBy;

    private Instant createdAt;

    private Instant updatedAt;


}
