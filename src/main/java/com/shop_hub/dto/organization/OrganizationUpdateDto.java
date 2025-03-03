package com.shop_hub.dto.organization;

import lombok.Data;

@Data
public class OrganizationUpdateDto {

    private String name;

    private String code;

    private String logo;

    private String website;

    private String email;

    private String address;

    private String termsUrl;

    private String privacyUrl;
}
