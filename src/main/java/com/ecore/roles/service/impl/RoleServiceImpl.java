package com.ecore.roles.service.impl;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.mapper.RoleMapper;
import com.ecore.roles.model.Role;
import com.ecore.roles.model.Team;
import com.ecore.roles.model.User;
import com.ecore.roles.model.dto.MembershipDto;
import com.ecore.roles.model.dto.RoleDto;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipService;
import com.ecore.roles.service.RoleService;
import com.ecore.roles.service.TeamService;
import com.ecore.roles.service.UserService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MembershipService membershipService;
    private final TeamService teamService;
    private final UserService userService;



    public RoleServiceImpl(
            RoleRepository roleRepository,
            MembershipService membershipService,
            TeamService teamService,
            UserService userService) {
        this.roleRepository = roleRepository;
        this.membershipService = membershipService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        log.info("Creating role {}", roleDto.getName());
        Role role = RoleMapper.from(roleDto);

        if (roleRepository.findByName(role.getName()).isPresent()) {
            log.error("Role {} exists", roleDto.getName());
            throw new ResourceExistsException(Role.class);
        }

        return RoleMapper.from(roleRepository.save(role));
    }

    @Override
    public RoleDto getRole(@NonNull UUID roleId) {
        log.info("Getting role {}", roleId);
        Role role =  roleRepository.findById(roleId)
                        .orElseThrow(() -> {
                            log.error("Role {} does not exist", roleId);
                            throw new ResourceNotFoundException(Role.class, roleId);
                        });

        return RoleMapper.from(role);
    }

    @Override
    public List<RoleDto> getRoles() {
        log.info("Getting all roles");
        return roleRepository.findAll().stream()
                .map(RoleMapper::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> getRoles(UUID userId, UUID teamId) {
        log.info("Getting all roles of user {} and team {}", userId, teamId);
        log.info("Checking if user {} exists", userId);
        ofNullable(userService.getUser(userId))
                    .orElseThrow(() -> {
                        log.error("User {} does not exist", userId);
                        throw new ResourceNotFoundException(User.class, userId);
                    });
        log.info("Checking if team {} exists", teamId);
        ofNullable(teamService.getTeam(teamId))
                    .orElseThrow(() -> {
                        log.error("Team {} does not exist", teamId);
                        throw new ResourceNotFoundException(Team.class, teamId);
                    });

        log.info("Getting role ids for user {} and team {}", userId, teamId);
        List<UUID> roleIdsList = membershipService.getMemberships(userId, teamId).stream()
                                    .map(MembershipDto::getRoleId)
                                    .collect(Collectors.toList());

        log.info("Getting all roles for the retrieved id list");
        return roleRepository.findAllById(roleIdsList).stream()
                .map(RoleMapper::from)
                .collect(Collectors.toList());
    }
}
