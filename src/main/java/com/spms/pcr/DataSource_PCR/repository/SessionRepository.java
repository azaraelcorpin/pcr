package com.spms.pcr.DataSource_PCR.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spms.pcr.DataSource_PCR.model.Session;

public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findByEmail(String email);
    List<Session> findAll();

    Optional<Session> findBySessionId(String sessionId);
}
