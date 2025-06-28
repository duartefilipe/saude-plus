package com.saudeplus.repository;

import com.saudeplus.model.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {
    
    List<MealLog> findByPatientId(Long patientId);
    
    List<MealLog> findByPatientIdAndDate(Long patientId, LocalDate date);
    
    List<MealLog> findByPatientIdAndDateBetween(Long patientId, LocalDate startDate, LocalDate endDate);
    
    List<MealLog> findByPatientIdAndTurn(Long patientId, String turn);
    
    List<MealLog> findByPatientIdOrderByDateDesc(Long patientId);
} 