package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.entity.Role;
import com.apassignment.thejobs.repository.RoleRepository;
import com.apassignment.thejobs.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleId(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found!"));
    }

    @Override
    public Role createRole(String name) {
        return roleRepository.save(new Role(name));
    }
}
