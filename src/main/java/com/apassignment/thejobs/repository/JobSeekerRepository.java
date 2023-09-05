package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.JobSeeker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    Boolean existsByEmail(String email);
    JobSeeker findJobSeekerByEmail(String email);
    @Query(value = "select j from JobSeeker as j where j.firstName like %:name% or j.lastName like %:name%")
    Page<JobSeeker> findJobSeekersByFirstName(@Param("name") String name, PageRequest pageRequest);
    @Query(value = "select j from JobSeeker as j")
    Page<JobSeeker> getAllJobSeekersWithPagination(PageRequest pageRequest);
}
