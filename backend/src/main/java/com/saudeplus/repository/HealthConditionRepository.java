package com.saudeplus.repository;

import com.saudeplus.model.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthCondition, Long> {
    
    List<HealthCondition> findByPatientId(Long patientId);
    
    List<HealthCondition> findByPatientIdAndActive(Long patientId, boolean active);
    
    List<HealthCondition> findByPatientIdAndType(Long patientId, HealthCondition.ConditionType type);
    
    List<HealthCondition> findByPatientIdAndTypeAndActive(Long patientId, HealthCondition.ConditionType type, boolean active);
} 