package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class JobSeekerResponseDto {
    private Long JobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String remark;
    private Boolean isActive;
}
