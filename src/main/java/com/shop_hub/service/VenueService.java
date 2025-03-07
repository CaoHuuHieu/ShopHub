package com.shop_hub.service;

import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.dto.venue.VenueCreateDto;
import com.shop_hub.dto.venue.VenueDto;
import com.shop_hub.dto.venue.VenueUpdateDto;

public interface VenueService {

    PageableResponseDto<VenueDto> getVenues(PageableRequestDto filter);

    VenueDto getVenue(Long venueId);

    VenueDto createVenue(VenueCreateDto venue);

    VenueDto updateVenue(Long venueId, VenueUpdateDto venue);

    void updateVenueStatus(Long venueId);

}
