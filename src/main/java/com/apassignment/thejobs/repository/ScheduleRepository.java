package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.dto.ScheduleDto;
import com.apassignment.thejobs.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByConsultantConsultantId(Long consultantId);

    @Query(value = "select s.scheduleId from Schedule s order by s.scheduleId desc limit 1")
    Long getLastInertedScheduleId();
}
