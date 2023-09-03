package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.UserDto;
import com.apassignment.thejobs.entity.Role;
import com.apassignment.thejobs.entity.User;
import com.apassignment.thejobs.repository.UserRepository;
import com.apassignment.thejobs.service.RoleService;
import com.apassignment.thejobs.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        User user = findUserById(userId);
        Role role = roleService.findByRoleId(roleId);
        user.assignRoleToUser(role);
    }
}
