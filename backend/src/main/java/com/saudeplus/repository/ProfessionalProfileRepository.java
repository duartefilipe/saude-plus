package com.saudeplus.repository;

import com.saudeplus.model.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {
    
    Optional<ProfessionalProfile> findByUserId(Long userId);
    
    List<ProfessionalProfile> findBySpecialty(String specialty);
    
    List<ProfessionalProfile> findByActive(boolean active);
    
    List<ProfessionalProfile> findByVerified(boolean verified);
    
    List<ProfessionalProfile> findByActiveAndVerified(boolean active, boolean verified);
} 