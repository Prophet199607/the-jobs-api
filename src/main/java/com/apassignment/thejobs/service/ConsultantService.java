package com.apassignment.thejobs.service;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Consultant;

import java.util.List;

public interface ConsultantService {
    Consultant findConsultantById(Long consultantId);
    Consultant findConsultantByUser(Long userId);
    ResponseDto fetchConsultantById(Long consultantId);
    ResponseDto fetchConsultants();
    ResponseDto fetchAllConsultantsWithPagination(int page, int size);
    ResponseDto findConsultantsByName(String name, int page, int size);
    ResponseDto findConsultantsByJobTypeAndCountry(Long jobTypeId, Long countryId);
    ResponseDto loadConsultantByEmail(String email);
    ResponseDto createConsultant(Consultant consultantDto);
    ResponseDto updateConsultant(ConsultantDto consultantDto);
    ResponseDto removeConsultant(Long consultantId);

}
