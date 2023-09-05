package com.apassignment.thejobs.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class AuthResponseDto {
    public Long loggedUserId;
    public String token;
    public UserResponseDto user;
    List<String> roles;
}
