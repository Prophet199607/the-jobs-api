package com.apassignment.thejobs.service.impl;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.dto.ResponseDto;
import com.apassignment.thejobs.entity.Appointment;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private ModelMapper modelMapper;

    @Override
    public Consultant findConsultantById(Long consultantId) {
        return consultantRepository.findById(consultantId)
                .orElseThrow(() -> new EntityNotFoundException("Consultant not found!"));
    }

    @Override
    public ResponseDto fetchConsultants() {
        List<ConsultantDto> consultants = consultantRepository.findAll()
                .stream().map(consultant -> modelMapper.map(consultant, ConsultantDto.class))
                .collect(Collectors.toList());
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                consultants
        );
    }

    @Override
    public ResponseDto findConsultantsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Consultant> consultantsPage = consultantRepository.findConsultantsByFirstName(name, pageRequest);

        PageImpl<ConsultantDto> consultantSearchData = new PageImpl<>(consultantsPage.getContent().stream()
                .map(consultant -> modelMapper.map(consultant, ConsultantDto.class))
                .collect(Collectors.toList()), pageRequest, consultantsPage.getTotalElements());

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Success",
                consultantSearchData
        );
    }

    @Override
    public ResponseDto loadConsultantByEmail(String email) {
        Consultant consultant = consultantRepository.findConsultantsByEmail(email);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Success!",
                modelMapper.map(consultant, ConsultantDto.class)
        );
    }

    @Override
    public ResponseDto createConsultant(ConsultantDto consultantDto) {
        /* check whether the email is duplicate or not **/
        if (consultantRepository.existsByEmail(consultantDto.getEmail())) {
            return new ResponseDto(ResponseType.DUPLICATE_ENTRY, HttpStatus.CONFLICT, "Duplicate email found!", null);
        }

        Consultant consultant = consultantMapper.fromConsultantDto(consultantDto);
        Country country = modelMapper.map(consultantDto.getCountry(), Country.class);
        JobType jobType = modelMapper.map(consultantDto.getJobType(), JobType.class);
        consultant.setCountry(country);
        consultant.setJobType(jobType);

        Consultant savedConsultant = consultantRepository.save(consultant);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                "Consultant has been saved successfully!",
                modelMapper.map(savedConsultant, ConsultantDto.class)
        );
    }

    @Override
    public ResponseDto updateConsultant(ConsultantDto consultantDto) {
        Consultant loadedConsultant = findConsultantById(consultantDto.getConsultantId());
        Consultant consultant = modelMapper.map(consultantDto, Consultant.class);
        consultant.setJobType(loadedConsultant.getJobType());
        consultant.setCountry(loadedConsultant.getCountry());
        Consultant updatedConsultant = consultantRepository.save(consultant);
        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Consultant has been updated successfully!",
                modelMapper.map(updatedConsultant, ConsultantDto.class)
        );
    }

    @Override
    public ResponseDto removeConsultant(Long consultantId) {
        Consultant consultant = findConsultantById(consultantId);
        //:TODO uncomment this line after creating appointment service
//        for (Appointment appointment: consultant.getAppointments()) {
//            appointment.removeAppointment(appointment.getAppointmentId());
//        }
        consultantRepository.deleteById(consultantId);

        return new ResponseDto(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                "Consultant has been deleted successfully!",
                modelMapper.map(consultant, ConsultantDto.class)
        );

    }
}
