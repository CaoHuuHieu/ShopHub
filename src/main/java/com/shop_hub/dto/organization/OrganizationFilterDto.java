package com.shop_hub.dto.organization;

import com.shop_hub.dto.pageable.organization.PageableRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.BindParam;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationFilterDto extends PageableRequestDto {
    @BindParam
    private String name;
    @BindParam
    private String code;

}
