package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/job-seeker/{jobSeekerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseDto> loadAppointmentsByJobSeeker(@PathVariable Long jobSeekerId) {
        ResponseDto responseDto = appointmentService.loadAppointmentsByJobSeeker2(jobSeekerId);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseDto> createConsultant(@RequestBody AppointmentDto appointmentDto) {
        ResponseDto responseDto = appointmentService.createAppointment(appointmentDto);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
