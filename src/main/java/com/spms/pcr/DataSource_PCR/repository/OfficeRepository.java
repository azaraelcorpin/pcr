package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spms.pcr.DataSource_PCR.model.Office;

public interface OfficeRepository extends JpaRepository<Office, String> {

    Optional<Office> findByCode(String code);
    Optional<Office> findById(Long id);
    List<Office> findAll();

}
