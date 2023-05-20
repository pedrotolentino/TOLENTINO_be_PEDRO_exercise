package com.ecore.roles.web;

import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.model.dto.RoleDto;
import com.ecore.roles.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleRestController {

    private final RoleService roleService;

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.createRole(role));
    }

    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles(@RequestParam(required = false) UUID teamMemberId,
                                                  @RequestParam(required = false) UUID teamId) {
        List<RoleDto> roleList = isAFilteredRequest(teamMemberId, teamId) ?
                                    roleService.getRoles(teamMemberId, teamId) :
                                    roleService.getRoles();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleList);
    }

    @GetMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRoleById(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.getRole(roleId));
    }

    private boolean isAFilteredRequest(UUID teamMemberId, UUID teamId) {
        if (teamMemberId == null && teamId == null) return false;
        if (teamMemberId != null && teamId != null) return true;
        throw new InvalidArgumentException();
    }
}
