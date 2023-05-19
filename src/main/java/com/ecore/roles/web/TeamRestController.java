package com.ecore.roles.web;

import com.ecore.roles.mapper.TeamMapper;
import com.ecore.roles.model.dto.TeamDto;
import com.ecore.roles.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/teams")
public class TeamRestController {

    private final TeamService teamService;

    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teamService.getTeams().stream()
                        .map(TeamMapper::from)
                        .collect(Collectors.toList()));
    }

    @GetMapping(
            path = "/{teamId}",
            produces = {"application/json"})
    public ResponseEntity<TeamDto> getTeam(
            @PathVariable UUID teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TeamMapper.from(teamService.getTeam(teamId)));
    }

}
