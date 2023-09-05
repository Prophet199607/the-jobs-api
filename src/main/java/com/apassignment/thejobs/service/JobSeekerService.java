package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.JobSeekerDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.JobSeeker;

public interface JobSeekerService {
    JobSeeker findJobSeekerById(Long jobSeekerId);

    ResponseDto fetchJobSeekerById(Long consultantId);

    ResponseDto fetchJobSeekers();

    ResponseDto fetchAllJobSeekersWithPagination(int page, int size);

    ResponseDto findJobSeekersByName(String name, int page, int size);

    ResponseDto loadJobSeekerByEmail(String email);

    ResponseDto createJobSeeker(JobSeekerDto jobSeekerDto);

    ResponseDto updateJobSeeker(JobSeekerDto jobSeekerDto);

    ResponseDto removeJobSeeker(Long jobSeekerId);
}
