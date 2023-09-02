package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;

public interface AppointmentService {
    Appointment loadAppointmentById(Long appointmentId);

    ResponseDto createAppointment(AppointmentDto appointmentDto);
}
