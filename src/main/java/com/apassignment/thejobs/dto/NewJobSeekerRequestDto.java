package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class NewJobSeekerRequestDto {
    private Long jobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String remark;
    private Boolean isActive;
    private UserDto user;
}
