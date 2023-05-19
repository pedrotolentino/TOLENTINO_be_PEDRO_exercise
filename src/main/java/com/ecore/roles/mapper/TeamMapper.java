package com.ecore.roles.mapper;

import com.ecore.roles.model.Team;
import com.ecore.roles.model.dto.TeamDto;

public class TeamMapper {
    public static TeamDto from(Team team) {
        if (team == null) {
            return null;
        }
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .teamLeadId(team.getTeamLeadId())
                .teamMemberIds(team.getTeamMemberIds())
                .build();
    }
}
