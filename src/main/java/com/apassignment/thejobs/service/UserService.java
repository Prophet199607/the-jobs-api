package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.UserDto;
import com.apassignment.thejobs.entity.User;

public interface UserService {
    User findUserById(Long userId);
    User loadUserByEmail(String email);

    User createUser(UserDto userDto);

    void assignRoleToUser(Long userId, Long roleId);

}
