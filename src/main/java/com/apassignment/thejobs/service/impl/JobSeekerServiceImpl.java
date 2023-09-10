package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.*;
import com.apassignment.thejobs.entity.Appointment;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.JobSeeker;
import com.apassignment.thejobs.entity.User;
import com.apassignment.thejobs.repository.JobSeekerRepository;
import com.apassignment.thejobs.service.AppointmentService;
import com.apassignment.thejobs.service.JobSeekerService;
import com.apassignment.thejobs.service.UserService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobSeekerServiceImpl implements JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JobSeeker findJobSeekerById(Long jobSeekerId) {
        return jobSeekerRepository.findById(jobSeekerId).orElse(null);
    }

    @Override
    public JobSeeker findConsultantByUser(Long userId) {
        return jobSeekerRepository.findJobSeekerByUserId(userId);
    }

    @Override
    public ResponseDto fetchJobSeekerById(Long jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("Consultant with ID " + jobSeekerId + " not found"));
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                modelMapper.map(jobSeeker, JobSeekerResponseDto.class)
        );
    }

    @Override
    public ResponseDto fetchJobSeekers() {
        List<JobSeekerResponseDto> jobSeekers = jobSeekerRepository.findAll()
                .stream().map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerResponseDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                jobSeekers
        );
    }

    @Override
    public ResponseDto fetchAllJobSeekersWithPagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<JobSeeker> allJobSeekersWithPagination = jobSeekerRepository.getAllJobSeekersWithPagination(pageRequest);

        PageImpl<JobSeekerResponseDto> jobSeekerResponseDtos = new PageImpl<>(allJobSeekersWithPagination.getContent().stream()
                .map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerResponseDto.class))
                .collect(Collectors.toList()), pageRequest, allJobSeekersWithPagination.getTotalElements());

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                jobSeekerResponseDtos
        );
    }

    @Override
    public ResponseDto findJobSeekersByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<JobSeeker> jobSeekersByPage = jobSeekerRepository.findJobSeekersByFirstName(name, pageRequest);

        PageImpl<JobSeekerResponseDto> jobSeekerSearchData = new PageImpl<>(jobSeekersByPage.getContent().stream()
                .map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerResponseDto.class)).collect(Collectors.toList()),
                pageRequest, jobSeekersByPage.getTotalElements());

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                jobSeekerSearchData
        );
    }

    @Override
    public ResponseDto loadJobSeekerByEmail(String email) {
        JobSeeker jobSeeker = jobSeekerRepository.findJobSeekerByEmail(email);
        if (jobSeeker == null) return new ResponseDto(ResponseType.DATA_NOT_FOUND,
                HttpStatus.NOT_FOUND, "No job seeker found with " + email + "!", null);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success!",
                modelMapper.map(jobSeeker, JobSeekerResponseDto.class)
        );
    }

    @Override
    public ResponseDto createJobSeeker(JobSeekerDto jobSeekerDto) {
        if (jobSeekerRepository.existsByEmail(jobSeekerDto.getEmail())) {
            return new ResponseDto(ResponseType.DUPLICATE_ENTRY, HttpStatus.CONFLICT, "Duplicate email found!", null);
        }

        User user = userService.createUser(new UserDto(jobSeekerDto.getUser().getUserName(),
                jobSeekerDto.getUser().getFullName(),
                jobSeekerDto.getUser().getEmail(), jobSeekerDto.getUser().getPassword()));

        userService.assignRoleToUser(user.getUserId(), 4L);

        JobSeeker jobSeeker = modelMapper.map(jobSeekerDto, JobSeeker.class);
        jobSeeker.setUser(user);

        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Job Seeker has been saved successfully!",
                modelMapper.map(savedJobSeeker, JobSeekerResponseDto.class)
        );
    }

    @Override
    public ResponseDto updateJobSeeker(JobSeekerDto jobSeekerDto) {
        JobSeeker loadedJobSeeker = findJobSeekerById(jobSeekerDto.getJobSeekerId());
        if (loadedJobSeeker == null) return new ResponseDto(ResponseType.DATA_NOT_FOUND,
                HttpStatus.NOT_FOUND, "No job seeker found!", null);

        JobSeeker jobSeeker = modelMapper.map(jobSeekerDto, JobSeeker.class);
        jobSeeker.setUser(loadedJobSeeker.getUser());

        JobSeeker updatedJobSeeker = jobSeekerRepository.save(jobSeeker);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Job Seeker has been saved successfully!",
                modelMapper.map(updatedJobSeeker, JobSeekerResponseDto.class)
        );
    }

    @Override
    public ResponseDto removeJobSeeker(Long jobSeekerId) {
        JobSeeker jobSeeker = findJobSeekerById(jobSeekerId);
        if (jobSeeker == null) return new ResponseDto(ResponseType.DATA_NOT_FOUND,
                HttpStatus.NOT_FOUND, "No job seeker found with ID " + jobSeekerId + "!", null);

        List<Appointment> appointments = appointmentService.loadAppointmentsByJobSeeker(jobSeekerId);
        appointmentService.deleteAllAppointments(appointments);

        jobSeekerRepository.deleteById(jobSeekerId);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Job Seeker has been deleted successfully!",
                modelMapper.map(jobSeeker, JobSeekerResponseDto.class)
        );

    }
}
