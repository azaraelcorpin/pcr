package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spms.pcr.DataSource_PCR.model.PreApproveEmp;

public interface PreApproveEmpRepository extends JpaRepository<PreApproveEmp, String> {

    Optional<PreApproveEmp> findByEmail(String email);
    Optional<PreApproveEmp> findById(Long id);
    List<PreApproveEmp> findAll();

}
