package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spms.pcr.DataSource_PCR.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    void deleteById(Long id);

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="WITH tmp AS ( "+
        "SELECT  "+
        "oe.emp_id, "+ 
        "GROUP_CONCAT('[',o.code,'-',oe.position,']' SEPARATOR ',') AS position  "+
        "FROM office o, office_employee oe  "+
        "WHERE o.id = oe.office_id  "+
        "GROUP BY oe.emp_id "+
        ") "+
        "SELECT  "+
        "e.*,  "+
        "CONCAT(e.lname,', ',e.fname,' ',COALESCE(CONCAT(LEFT(e.mname,1),'.'),'')) AS fullname,  "+
        "t.position  "+
        "FROM employee e "+
        "LEFT JOIN  tmp t ON t.emp_id = e.id")
    List<Map<String,Object>> getAll();

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="WITH tmp AS ( "+ 
        "SELECT   "+
        "oe.emp_id, "+  
        "GROUP_CONCAT('[',o.code,'-',oe.roles,']' SEPARATOR ',') AS roles "+
        "FROM office o, office_employee oe   "+
        "WHERE o.id = oe.office_id   "+
        "GROUP BY oe.emp_id  "+
        ") "+
        "SELECT   "+
        "e.*,   "+
        "CONCAT(e.lname,', ',e.fname,' ',COALESCE(CONCAT(LEFT(e.mname,1),'.'),'')) AS fullname,   "+
        "t.roles  "+
        "FROM employee e  "+
        "LEFT JOIN  tmp t ON t.emp_id = e.id "+
        "WHERE  "+
        " e.lName LIKE %:empName% "+
        "OR e.fName LIKE %:empName% "+
        "OR e.mName LIKE %:empName% ")
    List<Map<String,Object>> searchEmployee(@Param("empName")String empName);


    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="WITH tmp AS ( "+ 
        "SELECT   "+
        "oe.emp_id, "+  
        "GROUP_CONCAT('[',o.code,'-',oe.roles,']' SEPARATOR ',') AS roles "+
        "FROM office o, office_employee oe   "+
        "WHERE o.id = oe.office_id   "+
        "GROUP BY oe.emp_id  "+
        ")  "+
        "SELECT   "+
        "e.id, e.email,   "+
        "CONCAT(e.lname,', ',e.fname,' ',COALESCE(CONCAT(LEFT(e.mname,1),'.'),'')) AS fullname,   "+
        "t.roles  "+
        "FROM employee e  "+
        "LEFT JOIN  tmp t ON t.emp_id = e.id "+
        "WHERE  "+
        " e.lName LIKE %:empName% "+
        "OR e.fName LIKE %:empName% "+
        "OR e.mName LIKE %:empName% ",
        countQuery = "WITH tmp AS ( "+ 
        "SELECT   "+
        "oe.emp_id, "+  
        "GROUP_CONCAT('[',o.code,'-',oe.roles,']' SEPARATOR ',') AS roles "+
        "FROM office o, office_employee oe   "+
        "WHERE o.id = oe.office_id   "+
        "GROUP BY oe.emp_id  "+
        ")  "+
        "SELECT   "+
        "COUNT(e.id) "+
        "FROM employee e  "+
        "LEFT JOIN  tmp t ON t.emp_id = e.id "+
        "WHERE  "+
        " e.lName LIKE %:empName% "+
        "OR e.fName LIKE %:empName% "+
        "OR e.mName LIKE %:empName% " )
    Page<Map<String,Object>> searchEmployeePage(@Param("empName")String empName, Pageable pageable);
}
