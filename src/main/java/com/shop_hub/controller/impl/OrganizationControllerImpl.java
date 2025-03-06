package com.shop_hub.controller.impl;

import com.shop_hub.controller.OrganizationController;
import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.pageable.organization.PageableRequestDto;
import com.shop_hub.dto.pageable.organization.PageableResponseDto;
import com.shop_hub.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationControllerImpl implements OrganizationController {

    private final OrganizationService organizationService;

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public PageableResponseDto<OrganizationDto> getOrganization(PageableRequestDto filter) {
        return organizationService.getOrganizations(filter);
    }


}
