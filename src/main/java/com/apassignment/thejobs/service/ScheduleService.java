package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.dto.ScheduleDto;
import com.apassignment.thejobs.entity.Appointment;
import com.apassignment.thejobs.entity.Schedule;

import java.text.ParseException;
import java.util.List;

public interface ScheduleService {
    Schedule findScheduleById(Long scheduleId);

    ResponseDto createSchedule(ScheduleDto scheduleDto);

    ResponseDto removeSchedule(Long scheduleId);

    ResponseDto loadSchedulesByConsultant(Long scheduleId);

    ResponseDto loadAvailableSchedulesByConsultant(Long consultantId);

    void deleteAllSchedules(List<Schedule> schedules);

    Long getLastInertedScheduleId();
}
