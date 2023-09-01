package com.apassignment.thejobs.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consultants")
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultant_id", nullable = false)
    private Long consultantId;

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();


}
