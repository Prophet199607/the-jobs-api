package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.JobSeekerDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    @Autowired
    private JobSeekerService jobSeekerService;
    @PostMapping
    public ResponseEntity<ResponseDto> createJobSeeker(@RequestBody JobSeekerDto jobSeekerDto) {
        ResponseDto responseDto = jobSeekerService.createJobSeeker(jobSeekerDto);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
