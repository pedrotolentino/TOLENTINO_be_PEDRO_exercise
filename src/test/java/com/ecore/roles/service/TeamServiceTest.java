package com.ecore.roles.service;

import com.ecore.roles.client.TeamClient;
import com.ecore.roles.model.Team;
import com.ecore.roles.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ecore.roles.utils.TestData.ORDINARY_CORAL_LYNX_TEAM;
import static com.ecore.roles.utils.TestData.ORDINARY_CORAL_LYNX_TEAM_UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamServiceImpl TeamsService;
    @Mock
    private TeamClient TeamClient;

    @Test
    void shouldGetTeamWhenTeamIdExists() {
        Team ordinaryCoralLynxTeam = ORDINARY_CORAL_LYNX_TEAM();
        when(TeamClient.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ordinaryCoralLynxTeam));
        assertNotNull(TeamsService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID));
    }
}
