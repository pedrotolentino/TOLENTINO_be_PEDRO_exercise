package com.ecore.roles.web;

import com.ecore.roles.model.dto.MembershipDto;
import com.ecore.roles.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles/memberships")
public class MembershipRestController {

    private final MembershipService membershipService;

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<MembershipDto> assignRoleToMembership(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto membership = membershipService.assignRoleToMembership(membershipDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(membership);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<MembershipDto>> getMemberships(
            @RequestParam UUID roleId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(membershipService.getMemberships(roleId));
    }
}
