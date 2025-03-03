package com.shop_hub.dto.pageable.organization;

import lombok.Data;
import org.springframework.web.bind.annotation.BindParam;

@Data
public class PageableRequestDto {
    @BindParam
    private int page = 0;
    @BindParam
    private int size = Integer.MAX_VALUE;
    @BindParam
    private String sort;
    @BindParam
    private String filters;
}
