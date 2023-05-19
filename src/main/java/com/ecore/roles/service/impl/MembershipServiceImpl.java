package com.ecore.roles.service.impl;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.exception.ValidationException;
import com.ecore.roles.mapper.MembershipMapper;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.model.dto.MembershipDto;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;

    public MembershipServiceImpl(
            MembershipRepository membershipRepository,
            RoleRepository roleRepository) {
        this.membershipRepository = membershipRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public MembershipDto assignRoleToMembership(MembershipDto membershipDto) {
        log.info("Assigning the role {} to a membership", membershipDto.getRoleId());
        Membership membership = MembershipMapper.from(membershipDto);

        if (membershipRepository.findByUserIdAndTeamId(membership.getUserId(), membership.getTeamId())
                .isPresent()) {
            log.error("Membership already created for the user {} and team {} ", membership.getUserId(), membership.getTeamId());
            throw new ResourceExistsException(Membership.class);
        }

        roleRepository.findById(membership.getRole().getId()).orElseThrow(() -> {
            log.error("Role {} not found", membership.getRole().getId());
            throw new ValidationException(Membership.class, new ResourceNotFoundException(Role.class, membership.getRole().getId()).getMessage());
        });
        return MembershipMapper.from(membershipRepository.save(membership));
    }

    @Override
    public List<MembershipDto> getMemberships(@NonNull UUID roleId) {
        log.info("Getting memberships for the role {}",  roleId);
        return membershipRepository.findByRoleId(roleId).stream()
                .map(MembershipMapper::from)
                .collect(Collectors.toList());
    }
}
