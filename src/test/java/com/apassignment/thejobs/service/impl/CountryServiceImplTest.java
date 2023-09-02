package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.CountryDto;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountryServiceImplTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private CountryServiceImpl countryService;

    @DisplayName("Test successful country creation")
    @Test
    void testCreateCountry_Success() {
        Country country = new Country();
        country.setCountryId(20L);
        country.setCountryName("Sri Lanka");
        country.setStatus(1);

        when(countryRepository.save(country))
                .thenReturn(country);

        Country actualCountry = countryService.createCountry(country);

        assertEquals(country, actualCountry);
        assertEquals(country.getCountryName(), actualCountry.getCountryName());
        assertEquals(1, actualCountry.getStatus());
    }
}