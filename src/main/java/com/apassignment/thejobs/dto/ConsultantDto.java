package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class ConsultantDto {
    private Long consultantId;
    private String firstName;
    private String lastName;
    private String email;
    private CountryDto country;
    private JobTypeDto jobType;
}
