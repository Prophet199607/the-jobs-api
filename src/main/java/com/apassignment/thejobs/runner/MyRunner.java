package com.apassignment.thejobs.runner;

import com.apassignment.thejobs.dto.AppointmentDto;
import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.entity.JobType;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.mapper.JobTypeMapper;
import com.apassignment.thejobs.service.AppointmentService;
import com.apassignment.thejobs.service.ConsultantService;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {


    @Autowired
    private CountryService countryService;

    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private JobTypeMapper jobTypeMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void run(String... args) throws Exception {
        createCountries();
        createJobTypes();
        createConsultant();
        createAppointment();
    }

    private void createCountries() {
        Arrays.asList("Australia", "USA", "Canada", "Japan", "Korea", "New Zealand", "UK", "Russia", "Germany")
                .forEach(country -> countryService.createCountry(new Country(country, 1)));
    }

    private void createJobTypes() {
        Arrays.asList("Software Engineering", "Mechanic", "Web Developer", "Plumber", "Electrical Technician")
                .forEach(jobType -> jobTypeService.createJobType(jobType, 1));
    }

    private void createConsultant() {
        ConsultantDto consultantDto = new ConsultantDto();
        consultantDto.setEmail("pasindu@gmail.com");
        consultantDto.setFirstName("John");
        consultantDto.setLastName("Doe");
        consultantDto.setIsAvailable(true);

        Country country = countryService.loadCountryById(7L);
        consultantDto.setCountry(countryMapper.fromCountry(country));

        JobType jobType = jobTypeService.loadJobTypeById(1L);
        consultantDto.setJobType(jobTypeMapper.fromJobType(jobType));

        consultantService.createConsultant(consultantDto);
    }

    private void createAppointment() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentDate(LocalDate.now());
        appointmentDto.setTimeFrom(LocalTime.now());
        appointmentDto.setTimeTo(LocalTime.now().plusHours(1));

        Consultant consultant = consultantService.findConsultantById(1L);
        appointmentDto.setConsultant(modelMapper.map(consultant, ConsultantDto.class));

        appointmentService.createAppointment(appointmentDto);

    }

}
