package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.entity.JobType;
import com.apassignment.thejobs.mapper.ConsultantMapper;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.mapper.JobTypeMapper;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.repository.CountryRepository;
import com.apassignment.thejobs.repository.JobTypeRepository;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConsultantServiceImplTest {


    @MockBean
    private ConsultantRepository consultantRepository;

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private ConsultantServiceImpl consultantService;

    @Autowired
    private CountryServiceImpl countryService;

    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private ConsultantMapper consultantMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private JobTypeMapper jobTypeMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    void testCreateConsultant_Success() {
        Consultant consultant = new Consultant();
        consultant.setEmail("johnDoe@gmail.com");
        consultant.setFirstName("John");
        consultant.setLastName("Doe");
        consultant.setIsAvailable(true);

        ConsultantDto consultantDto = consultantMapper.fromConsultant(consultant);

        Country country = new Country(10L, "USA", 1);
        consultantDto.setCountry(countryMapper.fromCountry(country));

        JobType jobType = new JobType(6L,"Full stack developer", 1);
        consultantDto.setJobType(jobTypeMapper.fromJobType(jobType));

        Consultant mappedConsultant = modelMapper.map(consultantDto, Consultant.class);

        when(consultantRepository.save(mappedConsultant))
                .thenReturn(mappedConsultant);

        ResponseDto actualConsultantResponse =
                consultantService.createConsultant(consultantDto);

        ConsultantDto actualConsultant = (ConsultantDto) actualConsultantResponse.getData();

        assertEquals(consultantDto.getFirstName(), actualConsultant.getFirstName());
        assertEquals(consultantDto.getLastName(), actualConsultant.getLastName());
        assertEquals(consultantDto.getEmail(), actualConsultant.getEmail());
        assertEquals(consultantDto.getIsAvailable(), actualConsultant.getIsAvailable());
        assertEquals(consultantDto.getCountry().getCountryName(), actualConsultant.getCountry().getCountryName());
        assertEquals(consultantDto.getJobType().getJobType(), actualConsultant.getJobType().getJobType());
        assertEquals("Consultant has been saved successfully!", actualConsultantResponse.getMessage());
        verify(consultantRepository, times(1)).save(mappedConsultant);
    }
}