package com.shop_hub.controller;

import com.shop_hub.dto.organization.OrganizationCreateDto;
import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.organization.OrganizationUpdateDto;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


public interface OrganizationController {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    PageableResponseDto<OrganizationDto> getOrganization(@BindParam PageableRequestDto filter);

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    OrganizationDto saveOrganization(@RequestBody @Validated OrganizationCreateDto org);

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN')")
    OrganizationDto updateOrganization(@PathVariable Long organizationId, @RequestBody @Validated OrganizationUpdateDto org);

    @DeleteMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateOrganizationStatus(@PathVariable Long organizationId);

}
