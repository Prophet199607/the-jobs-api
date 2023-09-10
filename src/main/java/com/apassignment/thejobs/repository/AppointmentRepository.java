package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.entity.Appointment;
import com.apassignment.thejobs.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByJobSeekerJobSeekerId(Long JobSeekerId);

    List<Appointment> findAppointmentsByJobSeekerJobSeekerId(@Param("JobSeekerId") Long JobSeekerId);

    @Query(value = "select a from Appointment as a where a.consultant.consultantId=:consultantId and a.status =:status")
    List<Appointment> findAppointmentsByConsultantIdAndStatus(@Param("consultantId") Long consultantId, @Param("status") int status);

    @Modifying
    @Query(value = "UPDATE Appointment as a SET a.status =:status, a.isAccepted =:isAccepted WHERE a.appointmentId =:appointmentId")
    void changeAppointmentStatus(@Param("appointmentId") Long appointmentId,
                                 @Param("status") int status,
                                 @Param("isAccepted") boolean isAccepted);

}
