package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class JobSeekerDto {
    private Long JobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
    private UserDto user;
}
