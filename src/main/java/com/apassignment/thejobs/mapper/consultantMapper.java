package com.apassignment.thejobs.mapper;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.entity.Consultant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class consultantMapper {
    public ConsultantDto fromConsultant(Consultant consultant) {
        ConsultantDto consultantDto = new ConsultantDto();
        BeanUtils.copyProperties(consultant, consultantDto);
        return consultantDto;
    }

//    public Consultant fromConsultantDto()
}
