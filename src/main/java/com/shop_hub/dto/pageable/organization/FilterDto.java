package com.shop_hub.dto.pageable.organization;

import lombok.Data;

import java.util.List;

@Data
public class FilterDto {
    private String field;
    private String value;
    private List<String> values;
    private String operator;
}
