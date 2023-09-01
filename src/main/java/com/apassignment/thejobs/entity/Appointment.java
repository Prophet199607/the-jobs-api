package com.apassignment.thejobs.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", referencedColumnName = "consultant_id", nullable = false)
    private Consultant consultant;

}
