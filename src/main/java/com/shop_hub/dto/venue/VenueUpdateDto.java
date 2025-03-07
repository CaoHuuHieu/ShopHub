package com.shop_hub.dto.venue;

import lombok.Data;

@Data
public class VenueUpdateDto {

    private String name;

    private String code;

    private String address;

    private String mobile;

    private String email;

    private Boolean status;

}
