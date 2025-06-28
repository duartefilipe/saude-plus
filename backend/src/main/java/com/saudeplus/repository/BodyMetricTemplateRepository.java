package com.saudeplus.repository;

import com.saudeplus.model.BodyMetricTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyMetricTemplateRepository extends JpaRepository<BodyMetricTemplate, Long> {
    
    List<BodyMetricTemplate> findByProfessionalId(Long professionalId);
    
    List<BodyMetricTemplate> findByProfessionalIdAndActive(Long professionalId, boolean active);
    
    List<BodyMetricTemplate> findByNameContainingIgnoreCase(String name);
} 