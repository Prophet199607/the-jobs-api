package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.ConsultantResponseDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.AppointmentService;
import com.apassignment.thejobs.service.ConsultantService;
import com.apassignment.thejobs.service.JobSeekerService;
import com.apassignment.thejobs.service.ScheduleService;
import com.apassignment.thejobs.util.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController {

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDto> getFiguresForAdmin() {
        Long totalConsultants = consultantService.getCountOfRecords();
        Long totalJobSeekers = jobSeekerService.getCountOfRecords();
        Long totalAppointments = appointmentService.getCountOfRecords();
        Long totalSchedules = scheduleService.getCountOfRecords();

        Map<String, Long> hashMap = new HashMap<>();
        hashMap.put("totalConsultants", totalConsultants);
        hashMap.put("totalJobSeekers", totalJobSeekers);
        hashMap.put("totalAppointments", totalAppointments);
        hashMap.put("totalSchedules", totalSchedules);

        ResponseDto responseDto = new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success!",
                hashMap
        );

        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @GetMapping("/consultant/{consultantId}")
    @PreAuthorize("hasAnyAuthority('ROLE_CONSULTANT')")
    public ResponseEntity<ResponseDto> getFiguresForConsultant(@PathVariable Long consultantId) {
        Long myScheduleCount = consultantService.getScheduleCount(consultantId);
        Long myAppointmentsCount = consultantService.getAppointmentsCount(consultantId);
        Long myNewAppointmentsCount = consultantService.getNewAppointmentsCount(consultantId);

        Map<String, Long> hashMap = new HashMap<>();
        hashMap.put("myScheduleCount", myScheduleCount);
        hashMap.put("myAppointmentsCount", myAppointmentsCount);
        hashMap.put("myNewAppointmentsCount", myNewAppointmentsCount);

        ResponseDto responseDto = new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success!",
                hashMap
        );

        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
