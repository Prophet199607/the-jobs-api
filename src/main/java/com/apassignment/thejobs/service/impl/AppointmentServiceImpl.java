package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.*;
import com.apassignment.thejobs.entity.*;
import com.apassignment.thejobs.repository.AppointmentRepository;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.repository.JobSeekerRepository;
import com.apassignment.thejobs.repository.ScheduleRepository;
import com.apassignment.thejobs.service.*;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private EmailSenderService emailSenderService;

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
        scheduleRepository.changeScheduleBookedStatus(appointment.getSchedule().getScheduleId(), true);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        JobSeeker jobSeeker = jobSeekerRepository.findById(appointmentDto.getJobSeeker().getJobSeekerId())
                .orElseThrow(() -> new EntityNotFoundException("Job Seeker not found!"));

        // send appointment confirmation email to the jobSeeker
        emailSenderService.sendEmail(jobSeeker.getEmail(),
                "Appointment has been saved successfully",
                "Your appointment has been on review. Once the consultant accept your appointment you will get an email");

        // send new appointment placement email to the relevant consultant
        String emailBody = "New appointment received" +
                "\n\r" +
                "click below link to see details " +
                "http://localhost:7000/dashboard/manage-slots";

        emailSenderService.sendEmail(consultant.getEmail(),
                "New Appointment Request",
                emailBody);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Appointment has been saved successfully!",
                modelMapper.map(savedAppointment, AppointmentResponseDto.class)
        );
    }

    @Override
    public ResponseDto createAppointmentByReceptionist(NewJobSeekerRequestDto newJobSeekerRequestDto) {
        ResponseDto jobSeeker = jobSeekerService.createJobSeeker(modelMapper.map(newJobSeekerRequestDto, JobSeekerDto.class));

        newJobSeekerRequestDto.setJobSeeker(modelMapper.map(jobSeeker.getData(), JobSeekerDto.class));

        return createAppointment(modelMapper.map(newJobSeekerRequestDto, AppointmentDto.class));
    }

    @Override
    public ResponseDto removeAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        Appointment appointment = loadAppointmentById(appointmentId);
        scheduleRepository.changeScheduleBookedStatus(appointment.getSchedule().getScheduleId(), true);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "success!",
                null
        );
    }

    @Override
    public List<Appointment> loadAppointmentsByJobSeeker(Long jobSeekerId) {
        return appointmentRepository.findByJobSeekerJobSeekerId(jobSeekerId);
    }

    @Override
    public ResponseDto loadAppointmentsByConsultantId(Long consultantId, int status) {
        List<AppointmentResponseDto> appointments = appointmentRepository.findAppointmentsByConsultantIdAndStatus(consultantId, status)
                .stream().map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "success!",
                appointments
        );
    }

    @Override
    public ResponseDto changeAppointmentStatus(Long appointmentId, int status, boolean isAccepted) {
        appointmentRepository.changeAppointmentStatus(appointmentId, status, isAccepted);
        Appointment appointment = loadAppointmentById(appointmentId);
        if (status == 3) {
            scheduleRepository.changeScheduleBookedStatus(appointment.getSchedule().getScheduleId(), false);
        }
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "success!",
                null
        );
    }

    @Override
    public ResponseDto loadAppointmentsByJobSeeker2(Long jobSeekerId) {
        List<AppointmentResponseDto> appointments = appointmentRepository.findAppointmentsByJobSeekerJobSeekerId(jobSeekerId)
                .stream().map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "success!",
                appointments
        );
    }

    @Override
    public void deleteAllAppointments(List<Appointment> appointments) {
        appointmentRepository.deleteAll(appointments);
    }
}
