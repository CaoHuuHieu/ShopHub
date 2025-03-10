package com.shop_hub.controller.impl;

import com.shop_hub.controller.VenueController;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.dto.venue.VenueCreateDto;
import com.shop_hub.dto.venue.VenueDto;
import com.shop_hub.dto.venue.VenueUpdateDto;
import com.shop_hub.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/venues")
@RequiredArgsConstructor
public class VenueControllerImpl implements VenueController {

    private final VenueService venueService;

    @Override
    public PageableResponseDto<VenueDto> getVenues(PageableRequestDto filter) {
        return venueService.getVenues(filter);
    }

    @Override
    public VenueDto getVenue(Long venueId) {
        return venueService.getVenue(venueId);
    }

    @Override
    public VenueDto createVenue(VenueCreateDto venue) {
        return venueService.createVenue(venue);
    }

    @Override
    public VenueDto updateVenue(Long venueId, VenueUpdateDto venue) {
        return venueService.updateVenue(venueId, venue);
    }

    @Override
    public void activateVenue(Long venueId) {
        venueService.updateVenueStatus(venueId);
    }
}
