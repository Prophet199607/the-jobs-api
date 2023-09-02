package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.entity.JobType;
import com.apassignment.thejobs.repository.JobTypeRepository;
import com.apassignment.thejobs.service.JobTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobTypeServiceImpl implements JobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Override
    public JobType loadJobTypeById(Long jobTypeId) {
        return jobTypeRepository.findById(jobTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Job Type not found!"));
    }

    @Override
    public JobType createJobType(String jobType, int status) {
        return jobTypeRepository.save(new JobType(jobType, status));
    }
}
