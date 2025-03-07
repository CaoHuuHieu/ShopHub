package com.shop_hub.mapper;

import com.shop_hub.dto.venue.VenueDto;
import com.shop_hub.model.Venue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    VenueDto toVenueDto(Venue venue);

}
