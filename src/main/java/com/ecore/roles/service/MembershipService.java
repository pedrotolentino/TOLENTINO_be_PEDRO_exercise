package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.dto.MembershipDto;

import java.util.List;
import java.util.UUID;

public interface MembershipService {

    MembershipDto assignRoleToMembership(MembershipDto membership) throws ResourceNotFoundException;

    List<MembershipDto> getMemberships(UUID roleId);

    List<MembershipDto> getMemberships(UUID userId, UUID teamId);
}
