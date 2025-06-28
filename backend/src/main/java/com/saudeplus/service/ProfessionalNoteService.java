package com.saudeplus.service;

import com.saudeplus.model.ProfessionalNote;
import com.saudeplus.model.Patient;
import com.saudeplus.model.User;
import com.saudeplus.repository.ProfessionalNoteRepository;
import com.saudeplus.repository.PatientRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProfessionalNoteService {
    
    @Autowired
    private ProfessionalNoteRepository professionalNoteRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public ProfessionalNote createNote(ProfessionalNote note) {
        Patient patient = patientRepository.findById(note.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        User professional = userRepository.findById(note.getProfessional().getId())
            .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        // Validar se o profissional tem permissão para criar anotações
        if (professional.getRole() != User.UserRole.PROFESSIONAL) {
            throw new RuntimeException("Apenas profissionais podem criar anotações");
        }
        
        note.setPatient(patient);
        note.setProfessional(professional);
        
        return professionalNoteRepository.save(note);
    }
    
    public ProfessionalNote findNoteById(Long noteId) {
        return professionalNoteRepository.findById(noteId)
            .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));
    }
    
    public List<ProfessionalNote> findNotesByProfessional(Long professionalId) {
        return professionalNoteRepository.findByProfessionalIdOrderByCreatedAtDesc(professionalId);
    }
    
    public List<ProfessionalNote> findNotesByPatient(Long patientId) {
        return professionalNoteRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }
    
    public List<ProfessionalNote> findNotesByProfessionalAndPatient(Long professionalId, Long patientId) {
        return professionalNoteRepository.findByProfessionalIdAndPatientId(professionalId, patientId);
    }
    
    public List<ProfessionalNote> findNotesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return professionalNoteRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    public ProfessionalNote updateNote(Long noteId, ProfessionalNote noteDetails) {
        ProfessionalNote note = findNoteById(noteId);
        
        note.setContent(noteDetails.getContent());
        
        return professionalNoteRepository.save(note);
    }
    
    public void deleteNote(Long noteId) {
        ProfessionalNote note = findNoteById(noteId);
        professionalNoteRepository.delete(note);
    }
    
    // Security validation methods
    public boolean canAccessNote(Long noteId, Long userId) {
        ProfessionalNote note = findNoteById(noteId);
        return note.getProfessional().getId().equals(userId);
    }
    
    public List<ProfessionalNote> findNotesByProfessionalForPatient(Long professionalId, Long patientId) {
        return professionalNoteRepository.findByProfessionalIdAndPatientId(professionalId, patientId);
    }
    
    public int countNotesByProfessional(Long professionalId) {
        return professionalNoteRepository.findByProfessionalId(professionalId).size();
    }
    
    public int countNotesByPatient(Long patientId) {
        return professionalNoteRepository.findByPatientId(patientId).size();
    }
} 