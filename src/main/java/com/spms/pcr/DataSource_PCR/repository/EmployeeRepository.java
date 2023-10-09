package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spms.pcr.DataSource_PCR.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();

}
