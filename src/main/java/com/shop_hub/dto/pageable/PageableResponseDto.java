package com.shop_hub.dto.pageable;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PageableResponseDto<T> {
    List<T> data;
    private int size;
    private int page;
    private int totalPages;
    private long totalElement;
}

