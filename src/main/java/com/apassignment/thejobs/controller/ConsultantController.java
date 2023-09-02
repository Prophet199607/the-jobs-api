package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultant")
public class ConsultantController {
    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/all")
    public List<ConsultantDto> findAllConsultants() {
        return consultantService.fetchInstructors();
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createConsultant(@RequestBody ConsultantDto consultantDto) {
        ResponseDto responseDto = consultantService.createConsultant(consultantDto);
        return  new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
