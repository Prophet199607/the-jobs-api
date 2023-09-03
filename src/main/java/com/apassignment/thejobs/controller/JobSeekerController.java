package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.JobSeekerDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-seeker")
public class JobSeekerController {
    @Autowired
    private JobSeekerService jobSeekerService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSULTANT')")
    public ResponseEntity<ResponseDto> findAllJobSeekers() {
        ResponseDto responseDto = jobSeekerService.fetchJobSeekers();
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> searchJobSeekers(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        ResponseDto consultantsByName = jobSeekerService.findJobSeekersByName(keyword, page, size);
        return new ResponseEntity<>(consultantsByName, consultantsByName.getStatus());
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createJobSeeker(@RequestBody JobSeekerDto jobSeekerDto) {
        ResponseDto responseDto = jobSeekerService.createJobSeeker(jobSeekerDto);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @DeleteMapping("/{jobSeekerId}")
    public ResponseEntity<ResponseDto> deleteJobSeeker(@PathVariable Long jobSeekerId) {
        ResponseDto responseDto = jobSeekerService.removeJobSeeker(jobSeekerId);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @PutMapping("/{jobSeekerId}")
    public ResponseEntity<ResponseDto> updateInstructor(@RequestBody JobSeekerDto jobSeekerDto, @PathVariable Long jobSeekerId) {
        jobSeekerDto.setJobSeekerId(jobSeekerId);
        ResponseDto responseDto = jobSeekerService.updateJobSeeker(jobSeekerDto);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseDto> loadConsultantByEmail(@RequestParam(name = "email", defaultValue = "") String email) {
        ResponseDto responseDto = jobSeekerService.loadJobSeekerByEmail(email);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
