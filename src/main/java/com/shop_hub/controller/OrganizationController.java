package com.shop_hub.controller;


import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.pageable.organization.PageableRequestDto;
import com.shop_hub.dto.pageable.organization.PageableResponseDto;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.GetMapping;


public interface OrganizationController {


    @GetMapping
    PageableResponseDto<OrganizationDto> getOrganization(@BindParam PageableRequestDto filter);

}
