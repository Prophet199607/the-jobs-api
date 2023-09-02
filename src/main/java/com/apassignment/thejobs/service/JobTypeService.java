package com.apassignment.thejobs.service;

import com.apassignment.thejobs.entity.JobType;

public interface JobTypeService {
    JobType loadJobTypeById(Long jobTypeId);
    JobType createJobType(String jobType, int status);
}
