package com.saudeplus.repository;

import com.saudeplus.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByUserId(Long userId);
    
    List<Patient> findByGender(Patient.Gender gender);
    
    List<Patient> findByActive(boolean active);
} 