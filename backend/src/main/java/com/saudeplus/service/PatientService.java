package com.saudeplus.service;

import com.saudeplus.model.Patient;
import com.saudeplus.model.User;
import com.saudeplus.repository.PatientRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Patient createPatient(Patient patient) {
        // Validar se o usuário existe e é um paciente
        User user = userRepository.findById(patient.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (user.getRole() != User.UserRole.PATIENT) {
            throw new RuntimeException("Apenas pacientes podem ter perfis de paciente");
        }
        
        // Validar se já existe um perfil para este usuário
        if (patientRepository.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("Usuário já possui um perfil de paciente");
        }
        
        // Validar data de nascimento
        if (patient.getBirthDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de nascimento não pode ser no futuro");
        }
        
        patient.setUser(user);
        return patientRepository.save(patient);
    }
    
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado: " + id));
        
        patient.setBirthDate(patientDetails.getBirthDate());
        patient.setGender(patientDetails.getGender());
        patient.setNotes(patientDetails.getNotes());
        patient.setProfileImageUrl(patientDetails.getProfileImageUrl());
        patient.setActive(patientDetails.isActive());
        
        return patientRepository.save(patient);
    }
    
    public Patient findById(Long id) {
        return patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado: " + id));
    }
    
    public Optional<Patient> findByUserId(Long userId) {
        return patientRepository.findByUserId(userId);
    }
    
    public List<Patient> findByGender(Patient.Gender gender) {
        return patientRepository.findByGender(gender);
    }
    
    public List<Patient> findActivePatients() {
        return patientRepository.findByActive(true);
    }
    
    public void deactivatePatient(Long id) {
        Patient patient = findById(id);
        patient.setActive(false);
        patientRepository.save(patient);
    }
    
    public int calculateAge(Patient patient) {
        return LocalDate.now().getYear() - patient.getBirthDate().getYear();
    }
} 