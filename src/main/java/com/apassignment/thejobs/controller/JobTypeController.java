package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.CountryService;
import com.apassignment.thejobs.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job-type")
public class JobTypeController {
    @Autowired
    private JobTypeService jobTypeService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSULTANT')")
    public ResponseEntity<ResponseDto> findAllConsultants() {
        ResponseDto responseDto = jobTypeService.fetchJobTypes();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
