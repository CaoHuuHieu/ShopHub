package com.shop_hub.controller;

import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.dto.venue.VenueCreateDto;
import com.shop_hub.dto.venue.VenueDto;
import com.shop_hub.dto.venue.VenueUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface VenueController {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    PageableResponseDto<VenueDto> getVenues(@BindParam PageableRequestDto filter);

    @GetMapping("/{venueId}")
    @PreAuthorize("isAuthenticated()")
    VenueDto getVenue(@PathVariable Long venueId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN')")
    VenueDto createVenue(@RequestBody VenueCreateDto venue);

    @PutMapping("/{venueId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN')")
    VenueDto updateVenue(@PathVariable Long venueId, @RequestBody VenueUpdateDto venue);

    @PutMapping("/{venueId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN')")
    void activateVenue(@PathVariable Long venueId);

}
