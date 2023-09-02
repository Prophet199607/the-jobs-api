package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.repository.CountryRepository;
import com.apassignment.thejobs.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public Country loadCountryById(Long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
    }

    @Override
    public Country createCountry(String countryName, int status) {
        return countryRepository.save(new Country(countryName, status));
    }
}
