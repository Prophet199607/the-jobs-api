package com.apassignment.thejobs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long countryId;

    @Basic
    @Column(name = "country_name")
    private String countryName;

    @Basic
    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<Consultant> consultants = new HashSet<>();

    public Country(String countryName, int status) {
        this.countryName = countryName;
        this.status = status;
    }
}
