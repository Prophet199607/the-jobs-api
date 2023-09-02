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
    @Column(name = "appointment_id", precision = 0)
    private Long appointmentId;

    @Basic
    @Column(name = "appointment_date", nullable = false, precision = 2)
    private LocalDate appointmentDate;

    @Basic
    @Column(name = "time_from", nullable = false, precision = 3)
    private LocalTime timeFrom;

    @Basic
    @Column(name = "time_to", nullable = false, precision = 4)
    private LocalTime timeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", referencedColumnName = "consultant_id", nullable = false)
    private Consultant consultant;

}
