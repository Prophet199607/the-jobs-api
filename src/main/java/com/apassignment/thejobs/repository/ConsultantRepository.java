package com.apassignment.thejobs.repository;

import com.apassignment.thejobs.dto.ConsultantDto;
import com.apassignment.thejobs.entity.Consultant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
    Boolean existsByEmail(@Param("email") String email);
    Consultant findConsultantsByEmail(String email);
    @Query(value = "select c from Consultant as c where c.firstName like %:name% or c.lastName like %:name%")
    Page<Consultant> findConsultantsByFirstName(@Param("name") String name, PageRequest pageRequest);
    @Query(value = "select c from Consultant c")
    Page<Consultant> getAllConsultantsWithPagination(PageRequest pageRequest);

    @Query(value = "select c.* from consultants as c where c.user_id=:userId", nativeQuery = true)
    Consultant findConsultantByUserId(@Param("userId") Long userId);
}
