package com.apassignment.thejobs.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private Long appointmentId;
    private LocalDate appointmentDate;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private ConsultantDto consultant;
}
