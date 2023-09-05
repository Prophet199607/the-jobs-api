package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Consultant;

public interface ConsultantService {
    Consultant findConsultantById(Long consultantId);

    ResponseDto fetchConsultantById(Long consultantId);

    ResponseDto fetchConsultants();

    ResponseDto findConsultantsByName(String name, int page, int size);

    ResponseDto loadConsultantByEmail(String email);

    ResponseDto createConsultant(ConsultantDto consultantDto);

    ResponseDto updateConsultant(ConsultantDto consultantDto);

    ResponseDto removeConsultant(Long consultantId);

}
