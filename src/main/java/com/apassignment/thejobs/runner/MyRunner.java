package com.apassignment.thejobs.runner;

import com.apassignment.thejobs.dto.*;
import com.apassignment.thejobs.entity.*;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.mapper.JobTypeMapper;
import com.apassignment.thejobs.service.*;
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
    private JobSeekerService jobSeekerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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
        createRoles();
        createAdmin();
        createConsultant();
        createJobSeeker();
//        createAppointment();

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
        Consultant consultantDto = new Consultant();
        consultantDto.setEmail("pasinduxx@hotmail.com");
        consultantDto.setFirstName("John");
        consultantDto.setLastName("Doe");
        consultantDto.setContactNumber("0712381996");
        consultantDto.setIsAvailable(true);

        Country country = countryService.loadCountryById(7L);
        consultantDto.setCountry(country);

        JobType jobType = jobTypeService.loadJobTypeById(1L);
        consultantDto.setJobType(jobType);

        User userDto = new User();
        userDto.setUserName("john");
        userDto.setFullName("John Doe");
        userDto.setEmail("pasinduxx@hotmail.com");
        userDto.setPassword("1234");
        consultantDto.setUser(userDto);

        consultantService.createConsultant(consultantDto);
    }

    private void createAppointment() {
        AppointmentDto appointmentDto = new AppointmentDto();
//        appointmentDto.setAppointmentDate(LocalDate.now());
//        appointmentDto.setTimeFrom(LocalTime.now());
//        appointmentDto.setTimeTo(LocalTime.now().plusHours(1));
        appointmentDto.setStatus(0);
        appointmentDto.setIsAccepted(false);

        Consultant consultant = consultantService.findConsultantById(1L);
        appointmentDto.setConsultant(modelMapper.map(consultant, ConsultantDto.class));

        JobSeeker jobSeeker = jobSeekerService.findJobSeekerById(1L);
        appointmentDto.setJobSeeker(modelMapper.map(jobSeeker, JobSeekerDto.class));

        appointmentService.createAppointment(appointmentDto);
    }

    private void createRoles() {

        Arrays.asList("ROLE_ADMIN", "ROLE_CONSULTANT", "ROLE_RECEPTIONIST", "ROLE_USER")
                .forEach(role -> roleService.createRole(role));
    }

    private void createAdmin() {
        User admin = userService.createUser(new UserDto("admin", "Administrator", "admin@gmail.com", "1234"));
        userService.assignRoleToUser(admin.getUserId(), 1L);
    }

    private void createJobSeeker() {
        JobSeekerDto jobSeekerDto = new JobSeekerDto();
        jobSeekerDto.setEmail("dev7@onimtait.com");
        jobSeekerDto.setFirstName("Tom");
        jobSeekerDto.setLastName("Riddle");
        jobSeekerDto.setContactNumber("0712381996");
        jobSeekerDto.setIsActive(true);

        UserDto userDto = new UserDto();
        userDto.setUserName("tom");
        userDto.setFullName("Tom Riddle");
        userDto.setEmail("dev7@onimtait.com");
        userDto.setPassword("456");
        jobSeekerDto.setUser(userDto);

        jobSeekerService.createJobSeeker(jobSeekerDto);

    }

}
