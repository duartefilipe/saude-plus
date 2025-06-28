package com.saudeplus.repository;

import com.saudeplus.model.ProfessionalNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProfessionalNoteRepository extends JpaRepository<ProfessionalNote, Long> {
    
    List<ProfessionalNote> findByProfessionalId(Long professionalId);
    
    List<ProfessionalNote> findByPatientId(Long patientId);
    
    List<ProfessionalNote> findByProfessionalIdAndPatientId(Long professionalId, Long patientId);
    
    List<ProfessionalNote> findByProfessionalIdOrderByCreatedAtDesc(Long professionalId);
    
    List<ProfessionalNote> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    
    List<ProfessionalNote> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
} 