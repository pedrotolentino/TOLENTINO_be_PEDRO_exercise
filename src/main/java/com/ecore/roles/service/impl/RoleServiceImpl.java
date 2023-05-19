package com.ecore.roles.service.impl;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.mapper.RoleMapper;
import com.ecore.roles.model.Role;
import com.ecore.roles.model.dto.RoleDto;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.RoleService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(
            RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.from(roleDto);

        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }

        return RoleMapper.from(roleRepository.save(role));
    }

    @Override
    public RoleDto getRole(@NonNull UUID roleId) {
        Role role =  roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));

        return RoleMapper.from(role);
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::from)
                .collect(Collectors.toList());
    }
}
