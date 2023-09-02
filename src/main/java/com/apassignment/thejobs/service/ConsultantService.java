package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Consultant;

import java.util.List;

public interface ConsultantService {
    Consultant loadConsultantById(Long consultantId);

    List<ConsultantDto> fetchInstructors();

    ResponseDto createConsultant(ConsultantDto consultantDto);

}
