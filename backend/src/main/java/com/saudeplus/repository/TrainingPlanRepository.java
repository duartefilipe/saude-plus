package com.saudeplus.repository;

import com.saudeplus.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    
    List<TrainingPlan> findByPatientId(Long patientId);
    
    List<TrainingPlan> findByProfessionalId(Long professionalId);
    
    List<TrainingPlan> findByPatientIdAndProfessionalId(Long patientId, Long professionalId);
    
    List<TrainingPlan> findByPatientIdAndGoalContainingIgnoreCase(Long patientId, String goal);
    
    List<TrainingPlan> findByPatientIdAndStartDateBetween(Long patientId, LocalDate startDate, LocalDate endDate);
    
    List<TrainingPlan> findByPatientIdOrderByStartDateDesc(Long patientId);
} 