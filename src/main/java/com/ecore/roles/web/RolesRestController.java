package com.ecore.roles.web;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.model.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.model.dto.RoleDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController {

    private final RolesService rolesService;

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.CreateRole(role.toModel())));
    }

    @PostMapping(
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles() {

        List<Role> getRoles = rolesService.GetRoles();

        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : getRoles) {
            RoleDto roleDto = fromModel(role);
            roleDtoList.add(roleDto);
        }

        return ResponseEntity
                .status(200)
                .body(roleDtoList);
    }

    @PostMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.GetRole(roleId)));
    }

}
