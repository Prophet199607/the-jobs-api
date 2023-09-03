package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class JobSeekerResponseDto {
    private Long JobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
}
