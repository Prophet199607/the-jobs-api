package com.apassignment.thejobs.dto;

import lombok.Data;

@Data
public class AppointmentResponseDto {
    private Long appointmentId;
    private Boolean isAccepted;
    private Integer status;
    private ConsultantResponseDto consultant;
    private JobSeekerResponseDto jobSeeker;
    private ScheduleDto schedule;
}
