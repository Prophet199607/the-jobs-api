package com.apassignment.thejobs.service;

import com.apassignment.thejobs.entity.Country;

public interface CountryService {
    Country loadCountryById(Long countryId);
    Country createCountry(String countryName, int status);
}
