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

import com.spms.pcr.DataSource_PCR.model.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    Optional<Office> findByCode(String code);
    Optional<Office> findById(Long id);
    List<Office> findAll();
    void deleteById(Long id);

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="SELECT o.*,top.code AS top_office_code FROM office o LEFT JOIN office top ON o.top_office = top.id;")
    List<Map<String,Object>> getAll();

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="SELECT o.*,top.code AS top_office_code FROM office o LEFT JOIN office top ON o.top_office = top.id WHERE o.id =:id")
    Map<String,Object> getOfficeInfo(@Param("id")Long id);    

}
