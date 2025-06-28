package com.saudeplus.repository;

import com.saudeplus.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    
    List<MealPlan> findByPatientId(Long patientId);
    
    List<MealPlan> findByProfessionalId(Long professionalId);
    
    List<MealPlan> findByPatientIdAndProfessionalId(Long patientId, Long professionalId);
    
    List<MealPlan> findByPatientIdAndStartDateBetween(Long patientId, LocalDate startDate, LocalDate endDate);
    
    List<MealPlan> findByPatientIdOrderByStartDateDesc(Long patientId);
} 