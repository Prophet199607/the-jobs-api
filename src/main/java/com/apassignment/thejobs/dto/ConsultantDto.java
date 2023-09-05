package com.apassignment.thejobs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ConsultantDto {
    private Long consultantId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String remark;
    private Boolean isAvailable;
    private CountryDto country;
    private JobTypeDto jobType;
    private UserDto user;
}
