package com.saudeplus.controller;

import com.saudeplus.model.TrainingPlan;
import com.saudeplus.model.Exercise;
import com.saudeplus.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/training")
@Tag(name = "Treinos", description = "Endpoints para gerenciar planos de treino")
@CrossOrigin(origins = "*")
public class TrainingController {
    
    @Autowired
    private TrainingService trainingService;
    
    // TrainingPlan endpoints
    @PostMapping("/plans")
    @Operation(summary = "Criar plano de treino", description = "Cria um novo plano de treino")
    public ResponseEntity<?> createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        try {
            TrainingPlan createdPlan = trainingService.createTrainingPlan(trainingPlan);
            return ResponseEntity.ok(createdPlan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/plans/{planId}")
    @Operation(summary = "Buscar plano de treino", description = "Retorna um plano de treino específico")
    public ResponseEntity<TrainingPlan> getTrainingPlanById(@PathVariable Long planId) {
        try {
            TrainingPlan plan = trainingService.findTrainingPlanById(planId);
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/plans/patient/{patientId}")
    @Operation(summary = "Listar planos por paciente", description = "Retorna planos de treino de um paciente")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlansByPatient(@PathVariable Long patientId) {
        List<TrainingPlan> plans = trainingService.findTrainingPlansByPatient(patientId);
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/plans/professional/{professionalId}")
    @Operation(summary = "Listar planos por profissional", description = "Retorna planos de treino criados por um profissional")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlansByProfessional(@PathVariable Long professionalId) {
        List<TrainingPlan> plans = trainingService.findTrainingPlansByProfessional(professionalId);
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/plans/patient/{patientId}/goal")
    @Operation(summary = "Listar planos por objetivo", description = "Retorna planos de treino de um paciente por objetivo")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlansByGoal(
            @PathVariable Long patientId,
            @RequestParam String goal) {
        List<TrainingPlan> plans = trainingService.findTrainingPlansByPatientAndGoal(patientId, goal);
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/plans/patient/{patientId}/date-range")
    @Operation(summary = "Listar planos por período", description = "Retorna planos de treino de um paciente em um período")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlansByDateRange(
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TrainingPlan> plans = trainingService.findTrainingPlansByPatientAndDateRange(patientId, startDate, endDate);
        return ResponseEntity.ok(plans);
    }
    
    @PutMapping("/plans/{planId}")
    @Operation(summary = "Atualizar plano de treino", description = "Atualiza um plano de treino")
    public ResponseEntity<?> updateTrainingPlan(@PathVariable Long planId, @RequestBody TrainingPlan planDetails) {
        try {
            TrainingPlan updatedPlan = trainingService.updateTrainingPlan(planId, planDetails);
            return ResponseEntity.ok(updatedPlan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/plans/{planId}")
    @Operation(summary = "Deletar plano de treino", description = "Remove um plano de treino")
    public ResponseEntity<?> deleteTrainingPlan(@PathVariable Long planId) {
        try {
            trainingService.deleteTrainingPlan(planId);
            return ResponseEntity.ok().body("Plano de treino removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Exercise endpoints
    @PostMapping("/exercises")
    @Operation(summary = "Criar exercício", description = "Cria um novo exercício")
    public ResponseEntity<?> createExercise(@RequestBody Exercise exercise) {
        try {
            Exercise createdExercise = trainingService.createExercise(exercise);
            return ResponseEntity.ok(createdExercise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/exercises/{exerciseId}")
    @Operation(summary = "Buscar exercício", description = "Retorna um exercício específico")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long exerciseId) {
        try {
            Exercise exercise = trainingService.findExerciseById(exerciseId);
            return ResponseEntity.ok(exercise);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/exercises/plan/{trainingPlanId}")
    @Operation(summary = "Listar exercícios por plano", description = "Retorna exercícios de um plano de treino")
    public ResponseEntity<List<Exercise>> getExercisesByTrainingPlan(@PathVariable Long trainingPlanId) {
        List<Exercise> exercises = trainingService.findExercisesByTrainingPlan(trainingPlanId);
        return ResponseEntity.ok(exercises);
    }
    
    @GetMapping("/exercises/search")
    @Operation(summary = "Buscar exercícios por nome", description = "Busca exercícios por nome")
    public ResponseEntity<List<Exercise>> searchExercises(@RequestParam String name) {
        List<Exercise> exercises = trainingService.searchExercisesByName(name);
        return ResponseEntity.ok(exercises);
    }
    
    @GetMapping("/exercises/min-sets/{minSets}")
    @Operation(summary = "Buscar exercícios por séries mínimas", description = "Retorna exercícios com número mínimo de séries")
    public ResponseEntity<List<Exercise>> getExercisesByMinSets(@PathVariable Integer minSets) {
        List<Exercise> exercises = trainingService.findExercisesByMinSets(minSets);
        return ResponseEntity.ok(exercises);
    }
    
    @GetMapping("/exercises/min-reps/{minReps}")
    @Operation(summary = "Buscar exercícios por repetições mínimas", description = "Retorna exercícios com número mínimo de repetições")
    public ResponseEntity<List<Exercise>> getExercisesByMinReps(@PathVariable Integer minReps) {
        List<Exercise> exercises = trainingService.findExercisesByMinReps(minReps);
        return ResponseEntity.ok(exercises);
    }
    
    @PutMapping("/exercises/{exerciseId}")
    @Operation(summary = "Atualizar exercício", description = "Atualiza um exercício")
    public ResponseEntity<?> updateExercise(@PathVariable Long exerciseId, @RequestBody Exercise exerciseDetails) {
        try {
            Exercise updatedExercise = trainingService.updateExercise(exerciseId, exerciseDetails);
            return ResponseEntity.ok(updatedExercise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/exercises/{exerciseId}")
    @Operation(summary = "Deletar exercício", description = "Remove um exercício")
    public ResponseEntity<?> deleteExercise(@PathVariable Long exerciseId) {
        try {
            trainingService.deleteExercise(exerciseId);
            return ResponseEntity.ok().body("Exercício removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Utility endpoints
    @GetMapping("/plans/{planId}/stats")
    @Operation(summary = "Estatísticas do plano", description = "Retorna estatísticas de um plano de treino")
    public ResponseEntity<?> getPlanStats(@PathVariable Long planId) {
        try {
            int totalExercises = trainingService.calculateTotalExercises(planId);
            int totalSets = trainingService.calculateTotalSets(planId);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("planId", planId);
            stats.put("totalExercises", totalExercises);
            stats.put("totalSets", totalSets);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 