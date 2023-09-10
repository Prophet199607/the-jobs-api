package com.apassignment.thejobs.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private Long appointmentId;
    private Boolean isAccepted;
    private Integer status;
    private ConsultantDto consultant;
    private JobSeekerDto jobSeeker;
    private ScheduleDto schedule;
}
