package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class ConsultantResponseDto {
    private Long consultantId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String remark;
    private Boolean isAvailable;
    private CountryDto country;
    private JobTypeDto jobType;
}
