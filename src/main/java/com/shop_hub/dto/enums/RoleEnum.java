package com.shop_hub.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    SUPER_ADMIN("SUPER_ADMIN", 1),
    ORG_ADMIN("ORG_ADMIN", 2),
    VENUE_ADMIN("VENUE_ADMIN", 3);

    private final String roleCode;
    private final int priority;

}
