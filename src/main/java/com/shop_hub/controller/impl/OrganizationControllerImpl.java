package com.shop_hub.controller.impl;

import com.shop_hub.controller.OrganizationController;
import com.shop_hub.dto.organization.OrganizationCreateDto;
import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.organization.OrganizationUpdateDto;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationControllerImpl implements OrganizationController {

    private final OrganizationService organizationService;

    @Override
    public PageableResponseDto<OrganizationDto> getOrganization(PageableRequestDto filter) {
        return organizationService.getOrganizations(filter);
    }

    @Override
    public OrganizationDto saveOrganization(OrganizationCreateDto org) {
        return organizationService.createOrganization(org);
    }

    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationUpdateDto org) {
        return organizationService.updateOrganization(organizationId, org);
    }

    @Override
    public void updateOrganizationStatus(Long organizationId) {
        organizationService.updateOrganizationStatus(organizationId);
    }


}
