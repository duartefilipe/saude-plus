package com.saudeplus.service;

import com.saudeplus.model.*;
import com.saudeplus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TrainingService {
    
    @Autowired
    private TrainingPlanRepository trainingPlanRepository;
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // TrainingPlan methods
    public TrainingPlan createTrainingPlan(TrainingPlan trainingPlan) {
        Patient patient = patientRepository.findById(trainingPlan.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        User professional = userRepository.findById(trainingPlan.getProfessional().getId())
            .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        trainingPlan.setPatient(patient);
        trainingPlan.setProfessional(professional);
        
        return trainingPlanRepository.save(trainingPlan);
    }
    
    public TrainingPlan findTrainingPlanById(Long planId) {
        return trainingPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
    }
    
    public List<TrainingPlan> findTrainingPlansByPatient(Long patientId) {
        return trainingPlanRepository.findByPatientIdOrderByStartDateDesc(patientId);
    }
    
    public List<TrainingPlan> findTrainingPlansByProfessional(Long professionalId) {
        return trainingPlanRepository.findByProfessionalId(professionalId);
    }
    
    public List<TrainingPlan> findTrainingPlansByPatientAndGoal(Long patientId, String goal) {
        return trainingPlanRepository.findByPatientIdAndGoalContainingIgnoreCase(patientId, goal);
    }
    
    public List<TrainingPlan> findTrainingPlansByPatientAndDateRange(Long patientId, LocalDate startDate, LocalDate endDate) {
        return trainingPlanRepository.findByPatientIdAndStartDateBetween(patientId, startDate, endDate);
    }
    
    public TrainingPlan updateTrainingPlan(Long planId, TrainingPlan planDetails) {
        TrainingPlan plan = findTrainingPlanById(planId);
        
        plan.setGoal(planDetails.getGoal());
        plan.setStartDate(planDetails.getStartDate());
        plan.setEndDate(planDetails.getEndDate());
        plan.setNotes(planDetails.getNotes());
        
        return trainingPlanRepository.save(plan);
    }
    
    public void deleteTrainingPlan(Long planId) {
        TrainingPlan plan = findTrainingPlanById(planId);
        trainingPlanRepository.delete(plan);
    }
    
    // Exercise methods
    public Exercise createExercise(Exercise exercise) {
        TrainingPlan trainingPlan = findTrainingPlanById(exercise.getTrainingPlan().getId());
        exercise.setTrainingPlan(trainingPlan);
        return exerciseRepository.save(exercise);
    }
    
    public Exercise findExerciseById(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
            .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
    }
    
    public List<Exercise> findExercisesByTrainingPlan(Long trainingPlanId) {
        return exerciseRepository.findByTrainingPlanId(trainingPlanId);
    }
    
    public List<Exercise> searchExercisesByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Exercise> findExercisesByMinSets(Integer minSets) {
        return exerciseRepository.findBySetsGreaterThanEqual(minSets);
    }
    
    public List<Exercise> findExercisesByMinReps(Integer minReps) {
        return exerciseRepository.findByRepsGreaterThanEqual(minReps);
    }
    
    public Exercise updateExercise(Long exerciseId, Exercise exerciseDetails) {
        Exercise exercise = findExerciseById(exerciseId);
        
        exercise.setName(exerciseDetails.getName());
        exercise.setSets(exerciseDetails.getSets());
        exercise.setReps(exerciseDetails.getReps());
        exercise.setVideoUrl(exerciseDetails.getVideoUrl());
        exercise.setImageUrl(exerciseDetails.getImageUrl());
        
        return exerciseRepository.save(exercise);
    }
    
    public void deleteExercise(Long exerciseId) {
        Exercise exercise = findExerciseById(exerciseId);
        exerciseRepository.delete(exercise);
    }
    
    // Utility methods
    public int calculateTotalExercises(Long trainingPlanId) {
        return exerciseRepository.findByTrainingPlanId(trainingPlanId).size();
    }
    
    public int calculateTotalSets(Long trainingPlanId) {
        return exerciseRepository.findByTrainingPlanId(trainingPlanId)
            .stream()
            .mapToInt(Exercise::getSets)
            .sum();
    }
} 