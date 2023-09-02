package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.repository.AppointmentRepository;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.service.AppointmentService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Appointment loadAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found!"));
    }

    @Override
    public ResponseDto createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        Long consultantId = appointmentDto.getConsultant().getConsultantId();
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
        appointment.setConsultant(consultant);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Appointment has been saved successfully!",
                modelMapper.map(savedAppointment, AppointmentDto.class)
        );
    }
}
