package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.entity.Consultant;
import com.apassignment.thejobs.repository.ConsultantRepository;
import com.apassignment.thejobs.service.ConsultantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultantServiceImpl implements ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;

    @Override
    public Consultant loadConsultantById(Long consultantId) {
        return consultantRepository.findById(consultantId).orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
    }
}
