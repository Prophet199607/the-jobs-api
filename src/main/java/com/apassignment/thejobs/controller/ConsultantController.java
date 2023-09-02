package com.apassignment.thejobs.controller;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
