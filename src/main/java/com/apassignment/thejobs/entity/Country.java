package com.apassignment.thejobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public Country(Long countryId) {
        this.countryId = countryId;
    }

    public Country(Long countryId, String countryName, int status) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.status = status;
    }

    public Country(String countryName, int status) {
        this.countryName = countryName;
        this.status = status;
    }
}
