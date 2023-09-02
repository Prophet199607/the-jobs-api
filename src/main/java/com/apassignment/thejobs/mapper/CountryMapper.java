package com.apassignment.thejobs.mapper;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.CountryDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.Country;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto fromCountry(Country country) {
        CountryDto countryDto = new CountryDto();
        BeanUtils.copyProperties(country, countryDto);
        return countryDto;
    }

    public Country fromCountryDto(CountryDto countryDto) {
        Country country = new Country();
        BeanUtils.copyProperties(countryDto, country);
        return country;
    }
}
