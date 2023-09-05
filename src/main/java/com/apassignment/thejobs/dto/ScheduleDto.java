package com.apassignment.thejobs.dto;

import com.apassignment.thejobs.entity.Consultant;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class ScheduleDto {
    private Long scheduleId;
    private String title;
    private String start;
    private String end;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private Boolean isBooked = false;
    private ConsultantResponseDto consultant;
}
