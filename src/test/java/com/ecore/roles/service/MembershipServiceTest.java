package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ValidationException;
import com.ecore.roles.mapper.MembershipMapper;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.dto.MembershipDto;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.MembershipServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    private MembershipServiceImpl membershipsService;
    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private RoleRepository roleRepository;

    @Test
    public void shouldCreateMembership() {
        MembershipDto expectedMembershipDto = MembershipMapper.from(DEFAULT_MEMBERSHIP());
        Membership expectedMembership = DEFAULT_MEMBERSHIP();

        when(roleRepository.findById(expectedMembership.getRole().getId()))
                .thenReturn(Optional.ofNullable(DEVELOPER_ROLE()));
        when(membershipRepository.findByUserIdAndTeamId(expectedMembership.getUserId(),
                expectedMembership.getTeamId()))
                        .thenReturn(Optional.empty());
        when(membershipRepository.save(any(Membership.class)))
                        .thenReturn(expectedMembership);

        MembershipDto actualMembershipDto = membershipsService.assignRoleToMembership(expectedMembershipDto);

        assertNotNull(actualMembershipDto);
        assertEquals(actualMembershipDto, expectedMembershipDto);
        verify(roleRepository).findById(expectedMembership.getRole().getId());
    }

    @Test
    public void shouldFailToCreateMembershipWhenMembershipsIsNull() {
        assertThrows(NullPointerException.class,
                () -> membershipsService.assignRoleToMembership(null));
    }

    @Test
    public void shouldFailToCreateMembershipWhenItExists() {
        Membership expectedMembership = DEFAULT_MEMBERSHIP();
        when(membershipRepository.findByUserIdAndTeamId(expectedMembership.getUserId(),
                expectedMembership.getTeamId()))
                        .thenReturn(Optional.of(expectedMembership));

        ResourceExistsException exception = assertThrows(ResourceExistsException.class,
                () -> membershipsService.assignRoleToMembership(MembershipMapper.from(expectedMembership)));

        assertEquals("Membership already exists", exception.getMessage());
        verify(roleRepository, times(0)).findById(any());
    }

    @Test
    public void shouldFailToCreateMembershipWhenRoleIsNotFound() {
        MembershipDto expectedMembershipDto = MembershipMapper.from(DEFAULT_MEMBERSHIP());
        Membership expectedMembership = DEFAULT_MEMBERSHIP();

        when(membershipRepository.findByUserIdAndTeamId(expectedMembership.getUserId(),
                expectedMembership.getTeamId()))
                .thenReturn(Optional.empty());
        when(roleRepository.findById(expectedMembership.getRole().getId()))
                .thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> membershipsService.assignRoleToMembership(expectedMembershipDto));

        assertEquals(format("Invalid 'Membership' object. Reason: 'Role %s not found'", expectedMembership.getRole().getId()), exception.getMessage());
        verify(roleRepository, times(1)).findById(any());
    }

    @Test
    public void shouldGetMembershipList() {
        List<Membership> expectedMemberships = Collections.singletonList(DEFAULT_MEMBERSHIP());

        when(membershipRepository.findByRoleId(UUID_1))
                .thenReturn(expectedMemberships);

        List<MembershipDto> actualMemberships = membershipsService.getMemberships(UUID_1);

        assertNotNull(actualMemberships);
        assertTrue(actualMemberships.size() > 0);
        verify(membershipRepository, times(1)).findByRoleId(any(UUID.class));
    }

    @Test
    public void shouldGetEmptyMembershipList() {
        when(membershipRepository.findByRoleId(UUID_1))
                .thenReturn(Collections.emptyList());

        List<MembershipDto> actualMemberships = membershipsService.getMemberships(UUID_1);

        assertNotNull(actualMemberships);
        assertEquals(0, actualMemberships.size());
        verify(membershipRepository, times(1)).findByRoleId(any(UUID.class));
    }

    @Test
    public void shouldFailToGetMembershipsWhenRoleIdIsNull() {
        assertThrows(NullPointerException.class,
                () -> membershipsService.getMemberships(null));
    }

}
