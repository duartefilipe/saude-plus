package com.saudeplus.service;

import com.saudeplus.model.HealthCondition;
import com.saudeplus.model.Patient;
import com.saudeplus.repository.HealthConditionRepository;
import com.saudeplus.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HealthConditionService {
    
    @Autowired
    private HealthConditionRepository healthConditionRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    public HealthCondition createHealthCondition(HealthCondition condition) {
        Patient patient = patientRepository.findById(condition.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        condition.setPatient(patient);
        return healthConditionRepository.save(condition);
    }
    
    public HealthCondition findHealthConditionById(Long conditionId) {
        return healthConditionRepository.findById(conditionId)
            .orElseThrow(() -> new RuntimeException("Condição de saúde não encontrada"));
    }
    
    public List<HealthCondition> findHealthConditionsByPatient(Long patientId) {
        return healthConditionRepository.findByPatientId(patientId);
    }
    
    public List<HealthCondition> findActiveHealthConditionsByPatient(Long patientId) {
        return healthConditionRepository.findByPatientIdAndActive(patientId, true);
    }
    
    public List<HealthCondition> findHealthConditionsByPatientAndType(Long patientId, HealthCondition.ConditionType type) {
        return healthConditionRepository.findByPatientIdAndType(patientId, type);
    }
    
    public List<HealthCondition> findActiveHealthConditionsByPatientAndType(Long patientId, HealthCondition.ConditionType type) {
        return healthConditionRepository.findByPatientIdAndTypeAndActive(patientId, type, true);
    }
    
    public HealthCondition updateHealthCondition(Long conditionId, HealthCondition conditionDetails) {
        HealthCondition condition = findHealthConditionById(conditionId);
        
        condition.setType(conditionDetails.getType());
        condition.setTitle(conditionDetails.getTitle());
        condition.setDescription(conditionDetails.getDescription());
        condition.setStartDate(conditionDetails.getStartDate());
        condition.setEndDate(conditionDetails.getEndDate());
        condition.setActive(conditionDetails.isActive());
        
        return healthConditionRepository.save(condition);
    }
    
    public void deactivateHealthCondition(Long conditionId) {
        HealthCondition condition = findHealthConditionById(conditionId);
        condition.setActive(false);
        condition.setEndDate(LocalDate.now());
        healthConditionRepository.save(condition);
    }
    
    public void deleteHealthCondition(Long conditionId) {
        HealthCondition condition = findHealthConditionById(conditionId);
        healthConditionRepository.delete(condition);
    }
    
    // Utility methods for different condition types
    public List<HealthCondition> findLesionsByPatient(Long patientId) {
        return findHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.LESION);
    }
    
    public List<HealthCondition> findDiseasesByPatient(Long patientId) {
        return findHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.DISEASE);
    }
    
    public List<HealthCondition> findAllergiesByPatient(Long patientId) {
        return findHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.ALLERGY);
    }
    
    public List<HealthCondition> findMedicationsByPatient(Long patientId) {
        return findHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.MEDICATION);
    }
    
    public boolean hasActiveAllergies(Long patientId) {
        return !findActiveHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.ALLERGY).isEmpty();
    }
    
    public boolean hasActiveLesions(Long patientId) {
        return !findActiveHealthConditionsByPatientAndType(patientId, HealthCondition.ConditionType.LESION).isEmpty();
    }
} 