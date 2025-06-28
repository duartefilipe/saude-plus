package com.saudeplus.service;

import com.saudeplus.model.BodyMetricSession;
import com.saudeplus.model.BodyMetricEntry;
import com.saudeplus.model.Patient;
import com.saudeplus.model.User;
import com.saudeplus.repository.BodyMetricSessionRepository;
import com.saudeplus.repository.BodyMetricEntryRepository;
import com.saudeplus.repository.PatientRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BodyMetricService {
    
    @Autowired
    private BodyMetricSessionRepository sessionRepository;
    
    @Autowired
    private BodyMetricEntryRepository entryRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public BodyMetricSession createSession(BodyMetricSession session) {
        // Validar se o paciente existe
        Patient patient = patientRepository.findById(session.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        // Validar se o criador existe
        User createdBy = userRepository.findById(session.getCreatedBy().getId())
            .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado"));
        
        session.setPatient(patient);
        session.setCreatedBy(createdBy);
        
        return sessionRepository.save(session);
    }
    
    public BodyMetricSession findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + sessionId));
    }
    
    public List<BodyMetricSession> findSessionsByPatient(Long patientId) {
        return sessionRepository.findByPatientIdOrderByDateDesc(patientId);
    }
    
    public List<BodyMetricSession> findSessionsByPatientAndDateRange(Long patientId, LocalDate startDate, LocalDate endDate) {
        return sessionRepository.findByPatientIdAndDateBetween(patientId, startDate, endDate);
    }
    
    public BodyMetricEntry addEntryToSession(Long sessionId, BodyMetricEntry entry) {
        BodyMetricSession session = findSessionById(sessionId);
        entry.setSession(session);
        return entryRepository.save(entry);
    }
    
    public List<BodyMetricEntry> findEntriesBySession(Long sessionId) {
        return entryRepository.findBySessionIdOrderByDisplayOrderAsc(sessionId);
    }
    
    public BodyMetricEntry updateEntry(Long entryId, BodyMetricEntry entryDetails) {
        BodyMetricEntry entry = entryRepository.findById(entryId)
            .orElseThrow(() -> new RuntimeException("Entrada não encontrada: " + entryId));
        
        entry.setName(entryDetails.getName());
        entry.setUnit(entryDetails.getUnit());
        entry.setValue(entryDetails.getValue());
        entry.setPosition(entryDetails.getPosition());
        entry.setDisplayOrder(entryDetails.getDisplayOrder());
        
        return entryRepository.save(entry);
    }
    
    public void deleteEntry(Long entryId) {
        BodyMetricEntry entry = entryRepository.findById(entryId)
            .orElseThrow(() -> new RuntimeException("Entrada não encontrada: " + entryId));
        
        entryRepository.delete(entry);
    }
    
    public List<BodyMetricEntry> findEntriesByName(String metricName) {
        return entryRepository.findByName(metricName);
    }
    
    public void deleteSession(Long sessionId) {
        BodyMetricSession session = findSessionById(sessionId);
        sessionRepository.delete(session);
    }
} 