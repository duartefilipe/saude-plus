package com.saudeplus.repository;

import com.saudeplus.model.BodyMetricSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BodyMetricSessionRepository extends JpaRepository<BodyMetricSession, Long> {
    
    List<BodyMetricSession> findByPatientId(Long patientId);
    
    List<BodyMetricSession> findByPatientIdOrderByDateDesc(Long patientId);
    
    List<BodyMetricSession> findByPatientIdAndDateBetween(Long patientId, LocalDate startDate, LocalDate endDate);
    
    List<BodyMetricSession> findByCreatedById(Long createdById);
    
    List<BodyMetricSession> findByPatientIdAndCreatedById(Long patientId, Long createdById);
} 