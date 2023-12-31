package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.spms.pcr.DataSource_PCR.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndStatus(String email, String status);
    List<User> findAll();
    void deleteByEmail(String email);
    

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value="SELECT u.*, o.code AS office FROM user u LEFT JOIN office o ON u.office_id = o.id")
    List<Map<String,Object>> getAll();

    @QueryHints(@QueryHint(name =  org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(nativeQuery = true, value = "SELECT 'OFFICE_HEAD' AS position , office_id " +
           "FROM Employee e " +
           "WHERE e.email = :email AND e.position = 'OFFICE_HEAD' AND e.status = 'active' " +
           "UNION " +
           "SELECT 'INDIVIDUAL' AS position , office_id " +
           "FROM Employee e " +
           "WHERE e.email = :email")
    List<Map<String,Object>> getRoles(@Param("email") String email);
}
