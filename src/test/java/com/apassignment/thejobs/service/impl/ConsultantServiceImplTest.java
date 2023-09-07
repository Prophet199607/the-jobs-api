package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.dto.UserDto;
import com.apassignment.thejobs.entity.*;
import com.apassignment.thejobs.mapper.ConsultantMapper;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.mapper.JobTypeMapper;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.repository.CountryRepository;
import com.apassignment.thejobs.repository.JobTypeRepository;
import com.apassignment.thejobs.repository.RoleRepository;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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

    @MockBean
    private RoleRepository roleRepository;

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

    @BeforeEach
    void setUp() {
        when(roleRepository.findById(2L)).thenReturn(Optional.of(new Role(2L, "ROLE_CONSULTANT")));
    }

    @Transactional
    @Test
    void testCreateConsultant_Success() {
        Consultant consultant = new Consultant();
        consultant.setEmail("johnDoe@gmail.com");
        consultant.setFirstName("John");
        consultant.setLastName("Doe");
        consultant.setIsAvailable(true);

        ConsultantDto consultantDto = consultantMapper.fromConsultant(consultant);

        Country country = new Country(1L, "USA", 1);
        consultant.setCountry(country);

        JobType jobType = new JobType(1L,"Full stack developer", 1);
        consultant.setJobType(jobType);

        User userDto = new User();
        userDto.setUserName("john");
        userDto.setFullName("John Doe");
        userDto.setEmail("john@gmail.com");
        userDto.setPassword("1234");
        consultant.setUser(userDto);

//        Consultant mappedConsultant = modelMapper.map(consultantDto, Consultant.class);

        when(consultantRepository.save(consultant))
                .thenReturn(consultant);

        ResponseDto actualConsultantResponse =
                consultantService.createConsultant(consultant);

        Consultant actualConsultant =  modelMapper.map(actualConsultantResponse.getData(), Consultant.class);

        assertEquals(consultant.getFirstName(), actualConsultant.getFirstName());
        assertEquals(consultant.getLastName(), actualConsultant.getLastName());
        assertEquals(consultant.getEmail(), actualConsultant.getEmail());
        assertEquals(consultant.getIsAvailable(), actualConsultant.getIsAvailable());
        assertEquals(consultant.getCountry().getCountryName(), actualConsultant.getCountry().getCountryName());
        assertEquals(consultant.getJobType().getJobType(), actualConsultant.getJobType().getJobType());
        assertEquals("Consultant has been saved successfully!", actualConsultantResponse.getMessage());
        verify(consultantRepository, times(1)).save(consultant);
    }
}