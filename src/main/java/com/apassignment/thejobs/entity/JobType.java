package com.apassignment.thejobs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "job_types")
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobtype_id")
    private Long jobTypeId;

    @Basic
    @Column(name = "job_type")
    private String jobType;

    @Basic
    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "jobType", fetch = FetchType.LAZY)
    private Set<Consultant> consultants = new HashSet<>();

}
