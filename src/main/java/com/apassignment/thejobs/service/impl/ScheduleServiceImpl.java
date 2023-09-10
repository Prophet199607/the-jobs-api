package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantResponseDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.dto.ScheduleDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.Schedule;
import com.apassignment.thejobs.repository.ScheduleRepository;
import com.apassignment.thejobs.service.ConsultantService;
import com.apassignment.thejobs.service.ScheduleService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConsultantService consultantService;

    @Override
    public Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    @Override
    public ResponseDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        Long consultantId = scheduleDto.getConsultant().getConsultantId();
        Consultant consultant = consultantService.findConsultantById(consultantId);
        schedule.setConsultant(consultant);

        long newId = 1L;
        Long lastInsertedScheduleId = scheduleRepository.getLastInsertedScheduleId();
        if (lastInsertedScheduleId != null) {
            newId = lastInsertedScheduleId + 1;
        }
        schedule.setTitle("slot-" + String.format("%04d", newId));

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Schedule has been saved successfully!",
                modelMapper.map(savedSchedule, ScheduleDto.class)
        );
    }

    @Override
    public ResponseDto removeSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                null
        );
    }

    @Override
    public ResponseDto loadSchedulesByConsultant(Long consultantId) {
        List<ScheduleDto> schedules = scheduleRepository.findByConsultantConsultantId(consultantId)
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                schedules
        );
    }

    @Override
    public ResponseDto loadAvailableSchedulesByConsultant(Long consultantId) {
        List<ScheduleDto> schedules = scheduleRepository.findAvailableSchedulesByConsultantId(consultantId)
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                schedules
        );
    }

    @Override
    public void deleteAllSchedules(List<Schedule> schedules) {

    }

    @Override
    public Long getLastInertedScheduleId() {
        return scheduleRepository.getLastInsertedScheduleId();
    }
}
