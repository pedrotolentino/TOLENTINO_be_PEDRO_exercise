package com.ecore.roles.mapper;

import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.model.dto.MembershipDto;

import static java.util.Optional.ofNullable;

public class MembershipMapper {

    private MembershipMapper(){}

    public static MembershipDto from(Membership membership) {
        if (membership == null) {
            return null;
        }

        return MembershipDto.builder()
                .id(membership.getId())
                .roleId(ofNullable(membership.getRole()).map(Role::getId).orElse(null))
                .userId(membership.getUserId())
                .teamId(membership.getTeamId())
                .build();
    }

    public static Membership from(MembershipDto membershipDto) {
        if (membershipDto == null) {
            return null;
        }

        return Membership.builder()
                .id(membershipDto.getId())
                .role(Role.builder().id(membershipDto.getRoleId()).build())
                .userId(membershipDto.getUserId())
                .teamId(membershipDto.getTeamId())
                .build();
    }
}
