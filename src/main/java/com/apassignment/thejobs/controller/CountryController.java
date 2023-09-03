package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.ConsultantService;
import com.apassignment.thejobs.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSULTANT')")
    public ResponseEntity<ResponseDto> findAllConsultants() {
        ResponseDto responseDto = countryService.fetchCountries();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
