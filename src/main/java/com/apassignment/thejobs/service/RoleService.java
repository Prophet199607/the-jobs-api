package com.apassignment.thejobs.service;

import com.apassignment.thejobs.entity.Role;

public interface RoleService {

    Role findByRoleId(Long roleId);
    Role createRole(String name);
}
