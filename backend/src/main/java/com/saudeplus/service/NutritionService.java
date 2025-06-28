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
public class NutritionService {
    
    @Autowired
    private MealPlanRepository mealPlanRepository;
    
    @Autowired
    private MealLogRepository mealLogRepository;
    
    @Autowired
    private FoodItemRepository foodItemRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // MealPlan methods
    public MealPlan createMealPlan(MealPlan mealPlan) {
        Patient patient = patientRepository.findById(mealPlan.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        User professional = userRepository.findById(mealPlan.getProfessional().getId())
            .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        mealPlan.setPatient(patient);
        mealPlan.setProfessional(professional);
        
        return mealPlanRepository.save(mealPlan);
    }
    
    public MealPlan findMealPlanById(Long planId) {
        return mealPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plano alimentar não encontrado"));
    }
    
    public List<MealPlan> findMealPlansByPatient(Long patientId) {
        return mealPlanRepository.findByPatientIdOrderByStartDateDesc(patientId);
    }
    
    public List<MealPlan> findMealPlansByProfessional(Long professionalId) {
        return mealPlanRepository.findByProfessionalId(professionalId);
    }
    
    // MealLog methods
    public MealLog createMealLog(MealLog mealLog) {
        Patient patient = patientRepository.findById(mealLog.getPatient().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        mealLog.setPatient(patient);
        return mealLogRepository.save(mealLog);
    }
    
    public MealLog findMealLogById(Long logId) {
        return mealLogRepository.findById(logId)
            .orElseThrow(() -> new RuntimeException("Registro alimentar não encontrado"));
    }
    
    public List<MealLog> findMealLogsByPatientAndDate(Long patientId, LocalDate date) {
        return mealLogRepository.findByPatientIdAndDate(patientId, date);
    }
    
    public List<MealLog> findMealLogsByPatientAndDateRange(Long patientId, LocalDate startDate, LocalDate endDate) {
        return mealLogRepository.findByPatientIdAndDateBetween(patientId, startDate, endDate);
    }
    
    // FoodItem methods
    public FoodItem createFoodItem(FoodItem foodItem) {
        if (foodItemRepository.findByName(foodItem.getName()).isPresent()) {
            throw new RuntimeException("Alimento já cadastrado: " + foodItem.getName());
        }
        return foodItemRepository.save(foodItem);
    }
    
    public FoodItem findFoodItemById(Long foodId) {
        return foodItemRepository.findById(foodId)
            .orElseThrow(() -> new RuntimeException("Alimento não encontrado"));
    }
    
    public List<FoodItem> searchFoodItemsByName(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<FoodItem> findFoodItemsByCalorieRange(Double minKcal, Double maxKcal) {
        return foodItemRepository.findByKcalPer100gBetween(minKcal, maxKcal);
    }
    
    public List<FoodItem> findHighProteinFoods(Double minProtein) {
        return foodItemRepository.findByProteinPer100gGreaterThan(minProtein);
    }
    
    public List<FoodItem> findLowCarbFoods(Double maxCarbs) {
        return foodItemRepository.findByCarbsPer100gLessThan(maxCarbs);
    }
    
    // Utility methods
    public Double calculateCalories(FoodItem foodItem, Double quantityG) {
        return (foodItem.getKcalPer100g() * quantityG) / 100.0;
    }
    
    public Double calculateProtein(FoodItem foodItem, Double quantityG) {
        return (foodItem.getProteinPer100g() * quantityG) / 100.0;
    }
    
    public Double calculateCarbs(FoodItem foodItem, Double quantityG) {
        return (foodItem.getCarbsPer100g() * quantityG) / 100.0;
    }
    
    public Double calculateFat(FoodItem foodItem, Double quantityG) {
        return (foodItem.getFatPer100g() * quantityG) / 100.0;
    }
} 