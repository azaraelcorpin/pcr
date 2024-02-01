package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spms.pcr.DataSource_PCR.model.OfficeEmployee;

@Repository
public interface OfficeEmployeeRepository extends JpaRepository<OfficeEmployee, String> {

    List<OfficeEmployee> findByEmpId(Long empId);
    List<OfficeEmployee> findByOfficeId(Long officeId);
    Optional<OfficeEmployee> findTopByEmpId(Long empId);
    Optional<OfficeEmployee> findTopByOfficeId(Long empId);
    List<OfficeEmployee> findAll();
    void deleteByEmpId(Long id);
    void deleteByOfficeId(Long id);
    void deleteByEmpIdAndOfficeId(Long empId, Long officeId);

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="SELECT e.*,CONCAT(e.lname,', ',e.fname,' ',COALESCE(CONCAT(LEFT(e.mname,1),'.'),'')) AS fullname, oe.position, oe.roles FROM  office o, office_employee oe, employee e " + 
            "WHERE oe.emp_id = e.id " + 
            "AND oe.office_id = o.id " + 
            "AND o.id =:officeId")
    List<Map<String,Object>> getEmployeesByOffice(@Param("officeId")Long officeId);

}
