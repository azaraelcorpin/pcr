package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spms.pcr.DataSource_PCR.model.PcrSchedule;

@Repository
public interface PcrScheduleRepository extends JpaRepository<PcrSchedule, Long> {

    Optional<PcrSchedule> findById(Long id);
    List<PcrSchedule> findAll();
    void deleteById(Long id);

}
