package com.apassignment.thejobs.mapper;

import com.apassignment.thejobs.dto.CountryDto;
import com.apassignment.thejobs.dto.JobTypeDto;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.entity.JobType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class JobTypeMapper {
    public JobTypeDto fromJobType(JobType jobType) {
        JobTypeDto jobTypeDto = new JobTypeDto();
        BeanUtils.copyProperties(jobType, jobTypeDto);
        return jobTypeDto;
    }

    public JobType fromJobTypeDto(JobTypeDto jobTypeDto) {
        JobType jobType = new JobType();
        BeanUtils.copyProperties(jobTypeDto, jobType);
        return jobType;
    }
}
