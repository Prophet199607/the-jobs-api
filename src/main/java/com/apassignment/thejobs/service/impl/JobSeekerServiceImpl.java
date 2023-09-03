package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.JobSeekerDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.dto.UserDto;
import com.apassignment.thejobs.entity.JobSeeker;
import com.apassignment.thejobs.entity.User;
import com.apassignment.thejobs.repository.JobSeekerRepository;
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
    private ModelMapper modelMapper;

    @Override
    public JobSeeker findJobSeekerById(Long jobSeekerId) {
        return jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("Job Seeker not found!"));
    }

    @Override
    public ResponseDto fetchJobSeekers() {
        List<JobSeekerDto> jobSeekers = jobSeekerRepository.findAll()
                .stream().map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                jobSeekers
        );
    }

    @Override
    public ResponseDto findJobSeekersByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<JobSeeker> jobSeekersByPage = jobSeekerRepository.findJobSeekersByFirstName(name, pageRequest);

        PageImpl<JobSeekerDto> jobSeekerSearchData = new PageImpl<>(jobSeekersByPage.getContent().stream()
                .map(jobSeeker -> modelMapper.map(jobSeeker, JobSeekerDto.class)).collect(Collectors.toList()),
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
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Success!",
                modelMapper.map(jobSeeker, JobSeekerDto.class)
        );
    }

    @Override
    public ResponseDto createJobSeeker(JobSeekerDto jobSeekerDto) {
        if (jobSeekerRepository.existsByEmail(jobSeekerDto.getEmail())) {
            return new ResponseDto(ResponseType.DUPLICATE_ENTRY, HttpStatus.CONFLICT, "Duplicate email found!", null);
        }

        User user = userService.createUser(new UserDto(jobSeekerDto.getUser().getUserName(),
                jobSeekerDto.getUser().getEmail(), jobSeekerDto.getUser().getPassword()));

        userService.assignRoleToUser(user.getUserId(), 4L);

        JobSeeker jobSeeker = modelMapper.map(jobSeekerDto, JobSeeker.class);
        jobSeeker.setUser(user);

        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Job Seeker has been saved successfully!",
                modelMapper.map(savedJobSeeker, JobSeekerDto.class)
        );
    }

    @Override
    public ResponseDto updateJobSeeker(JobSeekerDto jobSeekerDto) {
        return null;
    }

    @Override
    public ResponseDto removeJobSeeker(Long jobSeekerId) {
        return null;
    }
}
