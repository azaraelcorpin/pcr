package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spms.pcr.DataSource_PCR.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findByEmail(String email);
    List<Session> findAll();

    Optional<Session> findBySessionId(String sessionId);

    Optional<Session> findBySessionIdAndEmail(String sessionId, String email);
}
