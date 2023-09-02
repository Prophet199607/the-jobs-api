package com.apassignment.thejobs.runner;

import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {


    @Autowired
    private CountryService countryService;

    @Autowired
    private JobTypeService jobTypeService;

    @Override
    public void run(String... args) throws Exception {
        createCountries();
        createJobTypes();
    }

    private void createCountries() {
        Arrays.asList("Australia", "USA", "Canada", "Japan", "Korea", "New Zealand", "UK", "Russia", "Germany")
                .forEach(country -> countryService.createCountry(country, 1));
    }

    private void createJobTypes() {
        Arrays.asList("Software Engineering", "Mechanic", "Web Developer", "Plumber", "Electrical Technician")
                .forEach(jobType -> jobTypeService.createJobType(jobType, 1));
    }
}
