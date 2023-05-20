package com.ecore.roles.service;

import com.ecore.roles.model.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleDto createRole(RoleDto role);

    RoleDto getRole(UUID id);

    List<RoleDto> getRoles();

    List<RoleDto> getRoles(UUID userId, UUID teamId);

}
