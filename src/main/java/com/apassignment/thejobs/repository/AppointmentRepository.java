package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByJobSeekerJobSeekerId(Long JobSeekerId);

    List<Appointment> findAppointmentsByJobSeekerJobSeekerId(@Param("JobSeekerId") Long JobSeekerId);
}
