package com.shop_hub.service.impl;

import com.shop_hub.dto.enums.RoleEnum;
import com.shop_hub.dto.organization.OrganizationCreateDto;
import com.shop_hub.dto.organization.OrganizationDto;
import com.shop_hub.dto.organization.OrganizationUpdateDto;
import com.shop_hub.dto.pageable.PageableRequestDto;
import com.shop_hub.dto.pageable.PageableResponseDto;
import com.shop_hub.exception.ForbiddenException;
import com.shop_hub.exception.NotFoundException;
import com.shop_hub.exception.ServerException;
import com.shop_hub.mapper.OrganizationMapper;
import com.shop_hub.model.*;
import com.shop_hub.repository.OrganizationRepository;
import com.shop_hub.service.AbstractService;
import com.shop_hub.service.OrganizationService;
import com.shop_hub.service.SystemLogService;
import com.shop_hub.util.AuthenticationUtils;
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
public class OrganizationServiceImpl
        extends AbstractService<Organization, OrganizationDto>
        implements OrganizationService
{

    private final OrganizationRepository organizationRepository;
    private final SystemLogService systemLogService;
    private final OrganizationMapper organizationMapper;


    @Override
    public PageableResponseDto<OrganizationDto> getOrganizations(PageableRequestDto filter) {
        return getPage(filter);
    }

    @Override
    public OrganizationDto getOrganization(Long orgId) {
        User currentUser = AuthenticationUtils.getCurrentUser();

        if(!currentUser.getRole().getRoleCode().equals(RoleEnum.SUPER_ADMIN.getRoleCode())
                &&
            (currentUser.getOrganization() == null || !currentUser.getOrganization().getId().equals(orgId)))
        {
            throw new ForbiddenException();
        }

        Organization organization = getOrganizationById(orgId);
        return mapToDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationCreateDto org) {
        User currentUser = AuthenticationUtils.getCurrentUser();
        Organization organization = Organization.builder()
                .name(org.getName())
                .code(org.getCode())
                .logo(org.getLogo())
                .website(org.getWebsite())
                .mobile(org.getMobile())
                .email(org.getEmail())
                .privacyUrl(org.getPrivacyUrl())
                .termsUrl(org.getTermsUrl())
                .address(org.getAddress())
                .createdBy(currentUser)
                .status(Organization.ACTIVATED)
                .build();

        try{
            organizationRepository.save(organization);
            systemLogService.createLog("CreateOrganization",
                    SystemLog.LOG_SUCCESS,
                    "Created Organization Successfully: " + organization.getId(),
                    organization.toString());
        } catch (Exception e){
            log.error("[OrganizationService][createOrganization] ERROR {}", e.getMessage());
            systemLogService.createLog("CreateOrganization", SystemLog.LOG_ERROR, e.getMessage(), organization.toString());
            throw new ServerException();
        }

        return mapToDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto updateOrganization(Long orgId, OrganizationUpdateDto org) {
        User currentUser = AuthenticationUtils.getCurrentUser();

        // OrgAdmin only can update their org!
        if(currentUser.getRole().getRoleCode().equals(RoleEnum.ORG_ADMIN.getRoleCode())
                && !currentUser.getOrganization().getId().equals(orgId))
                throw new ForbiddenException();

        Organization organization = getOrganizationById(orgId);
        organization.setName(org.getName());
        organization.setCode(org.getCode());
        organization.setLogo(org.getLogo());
        organization.setEmail(org.getEmail());
        organization.setPrivacyUrl(org.getPrivacyUrl());
        organization.setTermsUrl(org.getTermsUrl());
        organization.setAddress(org.getAddress());
        organization.setUpdatedBy(currentUser);
        try{
            organizationRepository.save(organization);
            systemLogService.createLog("UpdateOrganization",
                    SystemLog.LOG_SUCCESS,
                    "Updated Organization Successfully: " + organization.getId(),
                    organization.toString());
        } catch (Exception e){
            log.error("[OrganizationService][updateOrganization] ERROR {}", e.getMessage());
            systemLogService.createLog("UpdateOrganization", SystemLog.LOG_ERROR, e.getMessage(), organization.toString());
            throw new ServerException();
        }
        return mapToDto(organization);
    }

    @Override
    @Transactional
    public void updateOrganizationStatus(Long orgId) {
        User currentUser = AuthenticationUtils.getCurrentUser();
        Organization organization = getOrganizationById(orgId);
        organization.setStatus(organization.getStatus() == Organization.DELETED
                ?  Organization.ACTIVATED : Organization.DELETED);
        organization.setUpdatedBy(currentUser);
        try{
            organizationRepository.save(organization);
            systemLogService.createLog("UpdateOrganizationStatus",
                    SystemLog.LOG_SUCCESS,
                    "Remove Organization Status Successfully: " + organization.getId(),
                    organization.toString());
        } catch (Exception e){
            log.error("[OrganizationService][updateOrganizationStatus] ERROR {}", e.getMessage());
            systemLogService.createLog("UpdateOrganizationStatus", SystemLog.LOG_ERROR, e.getMessage(), organization.toString());
            throw new ServerException();
        }

    }

    private Organization getOrganizationById(Long orgId){
        return organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization Not Found: " + orgId));
    }

    @Override
    public OrganizationDto mapToDto(Organization organization) {
        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    public Page<Organization> getPage(Specification<Organization> specification, Pageable pageable) {
        return organizationRepository.findAll(specification, pageable);
    }

    @Override
    public Specification<Organization> createDefaultSpecification() {
        return (root, query, cb) -> {
            if(!AuthenticationUtils.getCurrentRole().equals(RoleEnum.SUPER_ADMIN.getRoleCode())) {
                return cb.and(cb.equal(root.get(AbstractEntity_.ID), AuthenticationUtils.getCurrentUser().getOrganization().getId()));
            }
            return cb.conjunction();
        };
    }

}
