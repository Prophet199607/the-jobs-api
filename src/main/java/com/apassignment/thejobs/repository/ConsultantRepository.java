package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
    Boolean existsByEmail(@Param("email") String email);
}
