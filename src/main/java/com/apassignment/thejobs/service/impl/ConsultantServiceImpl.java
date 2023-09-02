package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.entity.Country;
import com.apassignment.thejobs.entity.JobType;
import com.apassignment.thejobs.mapper.ConsultantMapper;
import com.apassignment.thejobs.mapper.CountryMapper;
import com.apassignment.thejobs.mapper.JobTypeMapper;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.service.ConsultantService;
import com.apassignment.thejobs.util.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConsultantServiceImpl implements ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ConsultantMapper consultantMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private JobTypeMapper jobTypeMapper;

    @Override
    public Consultant loadConsultantById(Long consultantId) {
        return consultantRepository.findById(consultantId).orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
    }

    @Override
    public List<ConsultantDto> fetchInstructors() {
        return consultantRepository.findAll().stream().map(consultant -> consultantMapper.fromConsultant(consultant)).collect(Collectors.toList());
    }

    @Override
    public ResponseDto createConsultant(ConsultantDto consultantDto) {
        /* check whether the email is duplicate or not **/
        if (consultantRepository.existsByEmail(consultantDto.getEmail())) {
            return new ResponseDto(ResponseType.DUPLICATE_ENTRY, HttpStatus.CONFLICT, "Duplicate email found!", null);
        }

        Consultant consultant = consultantMapper.fromConsultantDto(consultantDto);
        Country country = countryMapper.fromCountryDto(consultantDto.getCountry());
        JobType jobType = jobTypeMapper.fromJobTypeDto(consultantDto.getJobType());
        consultant.setCountry(country);
        consultant.setJobType(jobType);

        Consultant savedConsultant = consultantRepository.save(consultant);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Consultant has been saved successfully!",
                consultantMapper.fromConsultant(savedConsultant)
        );
    }
}
