package com.saudeplus.controller;

import com.saudeplus.model.Patient;
import com.saudeplus.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de pacientes")
@CrossOrigin(origins = "*")
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    @PostMapping
    @Operation(summary = "Criar perfil de paciente", description = "Cria um novo perfil de paciente")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        try {
            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.ok(createdPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar pacientes ativos", description = "Retorna lista de pacientes ativos")
    public ResponseEntity<List<Patient>> getActivePatients() {
        List<Patient> patients = patientService.findActivePatients();
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente específico")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            Patient patient = patientService.findById(id);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar paciente por usuário", description = "Retorna perfil de paciente pelo ID do usuário")
    public ResponseEntity<Patient> getPatientByUserId(@PathVariable Long userId) {
        return patientService.findByUserId(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/gender/{gender}")
    @Operation(summary = "Buscar pacientes por gênero", description = "Retorna pacientes por gênero")
    public ResponseEntity<List<Patient>> getPatientsByGender(@PathVariable String gender) {
        try {
            Patient.Gender patientGender = Patient.Gender.valueOf(gender.toUpperCase());
            List<Patient> patients = patientService.findByGender(patientGender);
            return ResponseEntity.ok(patients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}/age")
    @Operation(summary = "Calcular idade do paciente", description = "Calcula e retorna a idade de um paciente")
    public ResponseEntity<?> getPatientAge(@PathVariable Long id) {
        try {
            Patient patient = patientService.findById(id);
            int age = patientService.calculateAge(patient);
            
            Map<String, Object> response = new HashMap<>();
            response.put("patientId", id);
            response.put("age", age);
            response.put("birthDate", patient.getBirthDate());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza dados de um paciente")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patientDetails);
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Desativar paciente", description = "Desativa um perfil de paciente")
    public ResponseEntity<?> deactivatePatient(@PathVariable Long id) {
        try {
            patientService.deactivatePatient(id);
            return ResponseEntity.ok().body("Paciente desativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 