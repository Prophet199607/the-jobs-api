package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.mapper.ConsultantMapper;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.service.ConsultantService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Consultant loadConsultantById(Long consultantId) {
        return consultantRepository.findById(consultantId).orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
    }

    @Override
    public List<ConsultantDto> fetchInstructors() {
        return consultantRepository.findAll().stream().map(consultant -> consultantMapper.fromConsultant(consultant)).collect(Collectors.toList());
    }

    @Override
    public ConsultantDto createConsultant(ConsultantDto consultantDto) {
        return null;
    }
}
