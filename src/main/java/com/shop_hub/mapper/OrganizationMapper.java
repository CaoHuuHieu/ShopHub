package com.shop_hub.mapper;

import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.model.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationDto toOrganizationDto(Organization organization);

}
