package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Country;

public interface CountryService {
    Country loadCountryById(Long countryId);
    Country createCountry(Country country);
    ResponseDto fetchCountries();
}
