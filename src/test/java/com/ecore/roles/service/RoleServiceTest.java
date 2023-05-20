package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.mapper.MembershipMapper;
import com.ecore.roles.mapper.RoleMapper;
import com.ecore.roles.model.Role;
import com.ecore.roles.model.dto.RoleDto;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ecore.roles.utils.TestData.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl rolesService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MembershipService membershipService;

    @Mock
    private UserService userService;

    @Mock
    private TeamService teamService;

    @Test
    public void shouldCreateRole() {
        RoleDto expectedRole = RoleMapper.from(DEVELOPER_ROLE());

        when(roleRepository.findByName(expectedRole.getName()))
                .thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class)))
                .thenReturn(DEVELOPER_ROLE());

        RoleDto actualRole = rolesService.createRole(expectedRole);

        assertNotNull(actualRole);
        assertEquals(actualRole, expectedRole);
    }

    @Test
    public void shouldFailToCreateRoleWhenRoleIsNull() {
        assertThrows(NullPointerException.class,
                () -> rolesService.createRole(null));
    }

    @Test
    public void shouldFailToCreateRoleWhenRoleExists() {
        RoleDto expectedRole = RoleMapper.from(DEVELOPER_ROLE());

        when(roleRepository.findByName(expectedRole.getName()))
                .thenReturn(Optional.of(DEVELOPER_ROLE()));

        ResourceExistsException exception = assertThrows(ResourceExistsException.class,
                () -> rolesService.createRole(expectedRole));

        assertEquals("Role already exists", exception.getMessage());
        verify(roleRepository, times(1)).findByName(anyString());
    }

    @Test
    public void shouldReturnRoleWhenRoleIdExists() {
        Role developerRole = DEVELOPER_ROLE();

        when(roleRepository.findById(developerRole.getId())).thenReturn(Optional.of(developerRole));

        RoleDto actualRole = rolesService.getRole(developerRole.getId());

        assertNotNull(actualRole);
        assertEquals(actualRole, RoleMapper.from(developerRole));
    }

    @Test
    public void shouldFailToGetRoleWhenRoleIdDoesNotExist() {
        when(roleRepository.findById(UUID_1))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.getRole(UUID_1));

        assertEquals(format("Role %s not found", UUID_1), exception.getMessage());
        verify(roleRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void shouldGetRoleList() {
        List<Role> expectedRoles = Collections.singletonList(DEVELOPER_ROLE());

        when(roleRepository.findAll())
                .thenReturn(expectedRoles);

        List<RoleDto> actualRoles = rolesService.getRoles();

        assertNotNull(actualRoles);
        assertThat(actualRoles.size()).isGreaterThan(0);
    }

    @Test
    public void shouldGetEmptyRoleList() {
        when(roleRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<RoleDto> actualRoles = rolesService.getRoles();

        assertNotNull(actualRoles);
        assertEquals(0, actualRoles.size());
    }

    @Test
    public void shouldGetRoleListByUserIdAndTeamId() {
        List<RoleDto> expectedRoleList = Collections.singletonList(RoleMapper.from(DEVELOPER_ROLE()));

        when(userService.getUser(GIANNI_USER_UUID))
                .thenReturn(GIANNI_USER());
        when(teamService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(ORDINARY_CORAL_LYNX_TEAM());
        when(membershipService.getMemberships(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(Collections.singletonList(MembershipMapper.from(DEFAULT_MEMBERSHIP())));
        when(roleRepository.findAllById(Collections.singletonList(DEVELOPER_ROLE_UUID)))
                .thenReturn(Collections.singletonList(DEVELOPER_ROLE()));

        List<RoleDto> actualRole = rolesService.getRoles(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID);

        assertNotNull(actualRole);
        assertThat(actualRole.size()).isGreaterThan(0);
        assertThat(actualRole).contains(RoleMapper.from(DEVELOPER_ROLE()));
    }

    @Test
    public void shouldGetEmptyRoleListByUserIdAndTeamId() {
        List<RoleDto> expectedRoleList = Collections.singletonList(RoleMapper.from(DEVELOPER_ROLE()));

        when(userService.getUser(GIANNI_USER_UUID))
                .thenReturn(GIANNI_USER());
        when(teamService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(ORDINARY_CORAL_LYNX_TEAM());
        when(membershipService.getMemberships(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(Collections.singletonList(MembershipMapper.from(DEFAULT_MEMBERSHIP())));
        when(roleRepository.findAllById(Collections.singletonList(DEVELOPER_ROLE_UUID)))
                .thenReturn(Collections.emptyList());

        List<RoleDto> actualRole = rolesService.getRoles(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID);

        assertNotNull(actualRole);
        assertThat(actualRole.size()).isEqualTo(0);
    }

    @Test
    public void shouldFailToGetRoleListByUserIdAndTeamIdWhenUserDoesNotExists() {
        when(userService.getUser(GIANNI_USER_UUID))
                .thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.getRoles(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID));

        assertEquals(format("User %s not found", GIANNI_USER_UUID), exception.getMessage());
        verify(userService, times(1)).getUser(any(UUID.class));
    }

    @Test
    public void shouldFailToGetRoleListByUserIdAndTeamIdWhenTeamDoesNotExists() {
        when(userService.getUser(GIANNI_USER_UUID))
                .thenReturn(GIANNI_USER());
        when(teamService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.getRoles(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID));

        assertEquals(format("Team %s not found", ORDINARY_CORAL_LYNX_TEAM_UUID), exception.getMessage());
        verify(userService, times(1)).getUser(any(UUID.class));
        verify(teamService, times(1)).getTeam(any(UUID.class));
    }
}
