package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.model.Team;
import com.ecore.roles.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamClient teamClient;

    public TeamServiceImpl(TeamClient teamClient) {
        this.teamClient = teamClient;
    }

    public Team getTeam(UUID id) {
        return teamClient.getTeam(id).getBody();
    }

    public List<Team> getTeams() {
        return teamClient.getTeams().getBody();
    }
}
