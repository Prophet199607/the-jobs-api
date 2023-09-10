package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.NewJobSeekerRequestDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment loadAppointmentById(Long appointmentId);

    ResponseDto createAppointment(AppointmentDto appointmentDto);

    ResponseDto createAppointmentByReceptionist(NewJobSeekerRequestDto newJobSeekerRequestDto);

    ResponseDto removeAppointment(Long appointmentId);

    List<Appointment> loadAppointmentsByJobSeeker(Long jobSeekerId);

    ResponseDto loadAppointmentsByConsultantId(Long consultantId, int status);

    ResponseDto loadAllAppointments(String startDate, String endDate);

    ResponseDto changeAppointmentStatus(Long appointmentId, int status, boolean isAccepted);

    ResponseDto loadAppointmentsByJobSeeker2(Long jobSeekerId);

    void deleteAllAppointments(List<Appointment> appointments);
}
