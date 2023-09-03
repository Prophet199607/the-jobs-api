package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment loadAppointmentById(Long appointmentId);

    ResponseDto createAppointment(AppointmentDto appointmentDto);

    void removeAppointment(Long appointmentId);

    List<Appointment> loadAppointmentsByJobSeeker(Long jobSeekerId);

    void deleteAllAppointments(List<Appointment> appointments);
}
