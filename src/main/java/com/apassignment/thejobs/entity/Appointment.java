package com.apassignment.thejobs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Basic
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Basic
    @Column(name = "time_from", nullable = false)
    private LocalTime timeFrom;

    @Basic
    @Column(name = "time_to", nullable = false)
    private LocalTime timeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", referencedColumnName = "consultant_id", nullable = false)
    private Consultant consultant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobSeeker_id", referencedColumnName = "jobSeeker_id", nullable = false)
    private JobSeeker jobSeeker;

}
