package com.saudeplus.controller;

import com.saudeplus.model.HealthCondition;
import com.saudeplus.service.HealthConditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health-conditions")
@Tag(name = "Condições de Saúde", description = "Endpoints para gerenciar condições de saúde")
@CrossOrigin(origins = "*")
public class HealthConditionController {
    
    @Autowired
    private HealthConditionService healthConditionService;
    
    @PostMapping
    @Operation(summary = "Criar condição de saúde", description = "Cria uma nova condição de saúde")
    public ResponseEntity<?> createHealthCondition(@RequestBody HealthCondition healthCondition) {
        try {
            HealthCondition createdCondition = healthConditionService.createHealthCondition(healthCondition);
            return ResponseEntity.ok(createdCondition);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{conditionId}")
    @Operation(summary = "Buscar condição de saúde", description = "Retorna uma condição de saúde específica")
    public ResponseEntity<HealthCondition> getHealthConditionById(@PathVariable Long conditionId) {
        try {
            HealthCondition condition = healthConditionService.findHealthConditionById(conditionId);
            return ResponseEntity.ok(condition);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Listar condições por paciente", description = "Retorna condições de saúde de um paciente")
    public ResponseEntity<List<HealthCondition>> getHealthConditionsByPatient(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findHealthConditionsByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/type/{conditionType}")
    @Operation(summary = "Listar condições por tipo", description = "Retorna condições de saúde de um paciente por tipo")
    public ResponseEntity<List<HealthCondition>> getHealthConditionsByType(
            @PathVariable Long patientId,
            @PathVariable String conditionType) {
        try {
            HealthCondition.ConditionType type = HealthCondition.ConditionType.valueOf(conditionType.toUpperCase());
            List<HealthCondition> conditions = healthConditionService.findHealthConditionsByPatientAndType(patientId, type);
            return ResponseEntity.ok(conditions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/patient/{patientId}/active")
    @Operation(summary = "Listar condições ativas", description = "Retorna condições de saúde ativas de um paciente")
    public ResponseEntity<List<HealthCondition>> getActiveHealthConditions(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findActiveHealthConditionsByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/lesions")
    @Operation(summary = "Listar lesões", description = "Retorna lesões de um paciente")
    public ResponseEntity<List<HealthCondition>> getLesionsByPatient(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findLesionsByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/diseases")
    @Operation(summary = "Listar doenças", description = "Retorna doenças de um paciente")
    public ResponseEntity<List<HealthCondition>> getDiseasesByPatient(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findDiseasesByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/allergies")
    @Operation(summary = "Listar alergias", description = "Retorna alergias de um paciente")
    public ResponseEntity<List<HealthCondition>> getAllergiesByPatient(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findAllergiesByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/medications")
    @Operation(summary = "Listar medicações", description = "Retorna medicações de um paciente")
    public ResponseEntity<List<HealthCondition>> getMedicationsByPatient(@PathVariable Long patientId) {
        List<HealthCondition> conditions = healthConditionService.findMedicationsByPatient(patientId);
        return ResponseEntity.ok(conditions);
    }
    
    @GetMapping("/patient/{patientId}/has-allergies")
    @Operation(summary = "Verificar alergias ativas", description = "Verifica se o paciente tem alergias ativas")
    public ResponseEntity<Boolean> hasActiveAllergies(@PathVariable Long patientId) {
        boolean hasAllergies = healthConditionService.hasActiveAllergies(patientId);
        return ResponseEntity.ok(hasAllergies);
    }
    
    @GetMapping("/patient/{patientId}/has-lesions")
    @Operation(summary = "Verificar lesões ativas", description = "Verifica se o paciente tem lesões ativas")
    public ResponseEntity<Boolean> hasActiveLesions(@PathVariable Long patientId) {
        boolean hasLesions = healthConditionService.hasActiveLesions(patientId);
        return ResponseEntity.ok(hasLesions);
    }
    
    @PutMapping("/{conditionId}")
    @Operation(summary = "Atualizar condição de saúde", description = "Atualiza uma condição de saúde")
    public ResponseEntity<?> updateHealthCondition(@PathVariable Long conditionId, @RequestBody HealthCondition conditionDetails) {
        try {
            HealthCondition updatedCondition = healthConditionService.updateHealthCondition(conditionId, conditionDetails);
            return ResponseEntity.ok(updatedCondition);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{conditionId}/deactivate")
    @Operation(summary = "Desativar condição", description = "Desativa uma condição de saúde")
    public ResponseEntity<?> deactivateHealthCondition(@PathVariable Long conditionId) {
        try {
            healthConditionService.deactivateHealthCondition(conditionId);
            return ResponseEntity.ok().body("Condição desativada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{conditionId}")
    @Operation(summary = "Deletar condição", description = "Remove uma condição de saúde")
    public ResponseEntity<?> deleteHealthCondition(@PathVariable Long conditionId) {
        try {
            healthConditionService.deleteHealthCondition(conditionId);
            return ResponseEntity.ok().body("Condição removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 