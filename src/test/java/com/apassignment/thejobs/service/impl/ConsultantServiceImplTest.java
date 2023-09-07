package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ConsultantResponseDto;
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
import com.apassignment.thejobs.service.AppointmentService;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private AppointmentService appointmentService;

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

    @Test
    public void testLoadConsultantByEmail_ExistingConsultant() {
        String actualEmail = "test@example.com";
        Consultant mockConsultant = new Consultant();
        mockConsultant.setConsultantId(1L);
        mockConsultant.setEmail(actualEmail);

        when(consultantRepository.findConsultantsByEmail(actualEmail)).thenReturn(mockConsultant);

        ResponseDto response = consultantService.loadConsultantByEmail(actualEmail);


        assertEquals("Success!", response.getMessage());
        ConsultantResponseDto actualConsultantResponse = modelMapper.map(response.getData(), ConsultantResponseDto.class);
        assertEquals(1L, actualConsultantResponse.getConsultantId());
        assertEquals(actualEmail, actualConsultantResponse.getEmail());

        verify(consultantRepository, times(1)).findConsultantsByEmail(actualEmail);
    }

    @Test
    public void testDeleteConsultant() {
        Long consultantId = 1L;
        Consultant consultant = new Consultant();
        consultant.setConsultantId(consultantId);

        when(consultantRepository.findById(consultantId)).thenReturn(Optional.of(consultant));

        ResponseDto response = consultantService.removeConsultant(consultantId);

        assertEquals("Consultant has been deleted successfully!", response.getMessage());
        assertEquals(consultantId, ((ConsultantResponseDto) response.getData()).getConsultantId());

        verify(consultantRepository, times(1)).findById(consultantId);
        verify(consultantRepository, times(1)).deleteById(consultantId);
    }

    @Test
    public void testFetchConsultants() {
        Consultant consultant1 = new Consultant();
        consultant1.setConsultantId(1L);
        consultant1.setEmail("johnDoe@gmail.com");
        consultant1.setFirstName("John");
        consultant1.setLastName("Doe");
        consultant1.setIsAvailable(true);
        Consultant consultant2 = new Consultant();
        consultant1.setConsultantId(2L);
        consultant1.setEmail("jackma@gmail.com");
        consultant1.setFirstName("Jack");
        consultant1.setLastName("Ma");
        consultant1.setIsAvailable(true);
        List<Consultant> consultantList = Arrays.asList(consultant1, consultant2);

        when(consultantRepository.findAll()).thenReturn(consultantList);

        ResponseDto responseDto = consultantService.fetchConsultants();


        assertEquals("Success", responseDto.getMessage());
        List<ConsultantResponseDto> consultants = (List<ConsultantResponseDto>) responseDto.getData();
        assertEquals(2, consultants.size());

        ConsultantResponseDto consultantResponseDto1 = consultants.get(0);
        assertEquals(consultant1.getConsultantId(), consultantResponseDto1.getConsultantId());
        assertEquals(consultant1.getEmail(), consultantResponseDto1.getEmail());

        ConsultantResponseDto consultantResponseDto2 = consultants.get(1);
        assertEquals(consultant2.getConsultantId(), consultantResponseDto2.getConsultantId());
        assertEquals(consultant2.getEmail(), consultantResponseDto2.getEmail());
    }
}