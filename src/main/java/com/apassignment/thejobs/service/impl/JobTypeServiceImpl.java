package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.CountryDto;
import com.apassignment.thejobs.dto.JobTypeDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.JobType;
import com.apassignment.thejobs.repository.JobTypeRepository;
import com.apassignment.thejobs.service.JobTypeService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JobTypeServiceImpl implements JobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JobType loadJobTypeById(Long jobTypeId) {
        return jobTypeRepository.findById(jobTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Job Type not found!"));
    }

    @Override
    public JobType createJobType(String jobType, int status) {
        return jobTypeRepository.save(new JobType(jobType, status));
    }

    @Override
    public ResponseDto fetchJobTypes() {
        List<JobTypeDto> jobTypes = jobTypeRepository.findAll()
                .stream().map(country -> modelMapper.map(country, JobTypeDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                jobTypes
        );
    }
}
