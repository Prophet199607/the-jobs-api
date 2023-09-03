package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantResponseDto;
import com.apassignment.thejobs.dto.CountryDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.repository.CountryRepository;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Country loadCountryById(Long countryId) {
        return countryRepository.findById(countryId).orElseThrow(() -> new EntityNotFoundException("Country not found!"));
    }

    @Override
    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public ResponseDto fetchCountries() {
        List<CountryDto> countries = countryRepository.findAll()
                .stream().map(country -> modelMapper.map(country, CountryDto.class))
                .toList();
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                countries
        );
    }
}
