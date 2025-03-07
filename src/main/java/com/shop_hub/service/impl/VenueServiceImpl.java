package com.shop_hub.service.impl;

import com.shop_hub.dto.enums.RoleEnum;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.dto.venue.VenueCreateDto;
import com.shop_hub.dto.venue.VenueDto;
import com.shop_hub.dto.venue.VenueUpdateDto;
import com.shop_hub.exception.BadRequestException;
import com.shop_hub.exception.ForbiddenException;
import com.shop_hub.exception.NotFoundException;
import com.shop_hub.exception.ServerException;
import com.shop_hub.mapper.VenueMapper;
import com.shop_hub.model.*;
import com.shop_hub.repository.OrganizationRepository;
import com.shop_hub.repository.VenueRepository;
import com.shop_hub.service.AbstractService;
import com.shop_hub.service.SystemLogService;
import com.shop_hub.service.VenueService;
import com.shop_hub.util.AuthenticationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VenueServiceImpl
        extends AbstractService<Venue, VenueDto>
        implements VenueService
{

    private final VenueRepository venueRepository;
    private final OrganizationRepository organizationRepository;
    private final SystemLogService systemLogService;
    private final VenueMapper venueMapper;


    @Override
    public PageableResponseDto<VenueDto> getVenues(PageableRequestDto filter) {
        return getPage(filter);
    }

    @Override
    public VenueDto getVenue(Long venueId) {
        User currentAdmin = AuthenticationUtils.getCurrentUser();
        Venue venue = getVenueById(venueId);
        // This ORG_ADMIN can not get Venue of another ORG_ADMIN
        if( currentAdmin.getRole().getRoleCode().equals(RoleEnum.ORG_ADMIN.getRoleCode())
                &&
            !currentAdmin.getOrganization().getId().equals(venue.getId()))
        {
            throw new ForbiddenException();
        }
        return mapToDto(venue);
    }

    private Venue getVenueById(Long venueId) {
        return venueRepository.findById(venueId).orElseThrow(() -> new NotFoundException("Venue Not Found: " + venueId));
    }

    @Override
    @Transactional
    public VenueDto createVenue(VenueCreateDto venueDto) {
        User currentAdmin = AuthenticationUtils.getCurrentUser();
        Long orgId = Long.valueOf(AuthenticationUtils.getCurrentOrgId());

        //Check code is valid
        isValidVenueCode(venueDto.getCode());

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization Not Found: " + orgId));

        Venue venue = Venue.builder()
                .name(venueDto.getName())
                .email(venueDto.getEmail())
                .address(venueDto.getAddress())
                .mobile(venueDto.getMobile())
                .createdBy(currentAdmin)
                .organization(organization)
                .build();

        try{
            venueRepository.save(venue);
            systemLogService.createLog("CreateVenue",
                    SystemLog.LOG_SUCCESS,
                    "Created Venue Successfully: " + venue.getId(),
                    venue.toString());
        } catch (Exception e){
            log.error("[VenueService][createVenue] ERROR {}", e.getMessage());
            systemLogService.createLog("CreateVenue", SystemLog.LOG_ERROR, e.getMessage(), venue.toString());
            throw new ServerException();
        }

        return mapToDto(venue);
    }

    @Override
    @Transactional
    public VenueDto updateVenue(Long venueId, VenueUpdateDto venueDto) {
        User currentUser = AuthenticationUtils.getCurrentUser();

        //Check code is valid
        isValidVenueCode(venueDto.getCode());

        Venue venue = getVenueById(venueId);

        //This ORG_ADMIN can not update venue of another ORG_ADMIN
        if( currentUser.getRole().getRoleCode().equals(RoleEnum.ORG_ADMIN.getRoleCode())
                &&
            !venue.getOrganization().getId().equals(currentUser.getOrganization().getId()))
        {
            throw new ForbiddenException();
        }
        venue.setName(venueDto.getName());
        venue.setEmail(venueDto.getEmail());
        venue.setCode(venueDto.getCode());
        venue.setAddress(venueDto.getAddress());
        venue.setMobile(venueDto.getMobile());
        venue.setStatus(venueDto.getStatus());
        venue.setUpdatedBy(currentUser);

        try{
            venueRepository.save(venue);
            systemLogService.createLog("UpdateVenue",
                    SystemLog.LOG_SUCCESS,
                    "Updated Venue Successfully: " + venue.getId(),
                    venue.toString());
        } catch (Exception e){
            log.error("[VenueService][updateVenue] ERROR {}", e.getMessage());
            systemLogService.createLog("UpdateVenue", SystemLog.LOG_ERROR, e.getMessage(), venue.toString());
            throw new ServerException();
        }

        return mapToDto(venue);
    }

    private void isValidVenueCode(String venueCode) {
        boolean isValidCode = venueRepository.existsByCode(venueCode);
        if(isValidCode)
           return;
        throw new BadRequestException("This code already exists. Please try another one!");
    }

    @Override
    @Transactional
    public void updateVenueStatus(Long venueId) {
        User currentUser = AuthenticationUtils.getCurrentUser();
        Venue venue = getVenueById(venueId);

        if( currentUser.getRole().getRoleCode().equals(RoleEnum.ORG_ADMIN.getRoleCode())
                &&
            !venue.getOrganization().getId().equals(currentUser.getOrganization().getId()))
        {
            throw new ForbiddenException();
        }
        venue.setStatus(!venue.isStatus());
        venue.setUpdatedBy(currentUser);

        try{
            venueRepository.save(venue);
            systemLogService.createLog("UpdateVenueStatus",
                    SystemLog.LOG_SUCCESS,
                    "Updated Venue Status Successfully: " + venue.getId(),
                    venue.toString());
        } catch (Exception e){
            log.error("[VenueService][updateVenueStatus] ERROR {}", e.getMessage());
            systemLogService.createLog("UpdateVenueStatus", SystemLog.LOG_ERROR, e.getMessage(), venue.toString());
            throw new ServerException();
        }
    }

    @Override
    public VenueDto mapToDto(Venue venue) {
        return venueMapper.toVenueDto(venue);
    }

    @Override
    public Page<Venue> getPage(Specification<Venue> specification, Pageable pageable) {
        return venueRepository.findAll(specification, pageable);
    }

    @Override
    public Specification<Venue> createDefaultSpecification() {
        return (root, cq, cb) -> {
            User user = AuthenticationUtils.getCurrentUser();
            Predicate orgIdPredicate;
            if(user.getRole().getRoleCode().equals(RoleEnum.SUPER_ADMIN.getRoleCode())) {
                orgIdPredicate = cb.equal(root.get(Venue_.ORGANIZATION).get(AbstractEntity_.ID), AuthenticationUtils.getCurrentOrgId());
            } else {
                orgIdPredicate = cb.equal(root.get(Venue_.ORGANIZATION).get(AbstractEntity_.ID), user.getOrganization().getId());
            }
            return cb.and(orgIdPredicate);
        };
    }
}
