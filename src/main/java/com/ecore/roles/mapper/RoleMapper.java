package com.ecore.roles.mapper;

import com.ecore.roles.model.Role;
import com.ecore.roles.model.dto.RoleDto;

public class RoleMapper {

    private RoleMapper(){}

    public static RoleDto from(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role from(RoleDto roleDto) {
        if (roleDto == null){
            return null;
        }
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }
}
