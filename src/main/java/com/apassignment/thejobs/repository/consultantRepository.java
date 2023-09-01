package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface consultantRepository extends JpaRepository<Consultant, Long> {
}
