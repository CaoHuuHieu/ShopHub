package com.shop_hub.service;

import com.shop_hub.dto.organization.OrganizationCreateDto;
import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.organization.OrganizationUpdateDto;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;

public interface OrganizationService {

    PageableResponseDto<OrganizationDto> getOrganizations(PageableRequestDto filter);

    OrganizationDto getOrganization(Long orgId);

    OrganizationDto createOrganization(OrganizationCreateDto organization);

    OrganizationDto updateOrganization(Long orgId, OrganizationUpdateDto org);

    void updateOrganizationStatus(Long orgId);

}
