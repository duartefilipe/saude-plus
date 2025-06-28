package com.saudeplus.repository;

import com.saudeplus.model.BodyMetricEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyMetricEntryRepository extends JpaRepository<BodyMetricEntry, Long> {
    
    List<BodyMetricEntry> findBySessionId(Long sessionId);
    
    List<BodyMetricEntry> findBySessionIdOrderByDisplayOrderAsc(Long sessionId);
    
    List<BodyMetricEntry> findByName(String name);
    
    List<BodyMetricEntry> findBySessionIdAndName(Long sessionId, String name);
} 