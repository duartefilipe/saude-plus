package com.saudeplus.controller;

import com.saudeplus.model.*;
import com.saudeplus.service.NutritionService;
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
@RequestMapping("/nutrition")
@Tag(name = "Nutrição", description = "Endpoints para gerenciar nutrição")
@CrossOrigin(origins = "*")
public class NutritionController {
    
    @Autowired
    private NutritionService nutritionService;
    
    // MealPlan endpoints
    @PostMapping("/meal-plans")
    @Operation(summary = "Criar plano alimentar", description = "Cria um novo plano alimentar")
    public ResponseEntity<?> createMealPlan(@RequestBody MealPlan mealPlan) {
        try {
            MealPlan createdPlan = nutritionService.createMealPlan(mealPlan);
            return ResponseEntity.ok(createdPlan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/meal-plans/{planId}")
    @Operation(summary = "Buscar plano alimentar", description = "Retorna um plano alimentar específico")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long planId) {
        try {
            MealPlan plan = nutritionService.findMealPlanById(planId);
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/meal-plans/patient/{patientId}")
    @Operation(summary = "Listar planos por paciente", description = "Retorna planos alimentares de um paciente")
    public ResponseEntity<List<MealPlan>> getMealPlansByPatient(@PathVariable Long patientId) {
        List<MealPlan> plans = nutritionService.findMealPlansByPatient(patientId);
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/meal-plans/professional/{professionalId}")
    @Operation(summary = "Listar planos por profissional", description = "Retorna planos alimentares criados por um profissional")
    public ResponseEntity<List<MealPlan>> getMealPlansByProfessional(@PathVariable Long professionalId) {
        List<MealPlan> plans = nutritionService.findMealPlansByProfessional(professionalId);
        return ResponseEntity.ok(plans);
    }
    
    // MealLog endpoints
    @PostMapping("/meal-logs")
    @Operation(summary = "Criar registro alimentar", description = "Cria um novo registro alimentar")
    public ResponseEntity<?> createMealLog(@RequestBody MealLog mealLog) {
        try {
            MealLog createdLog = nutritionService.createMealLog(mealLog);
            return ResponseEntity.ok(createdLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/meal-logs/{logId}")
    @Operation(summary = "Buscar registro alimentar", description = "Retorna um registro alimentar específico")
    public ResponseEntity<MealLog> getMealLogById(@PathVariable Long logId) {
        try {
            MealLog log = nutritionService.findMealLogById(logId);
            return ResponseEntity.ok(log);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/meal-logs/patient/{patientId}/date/{date}")
    @Operation(summary = "Listar registros por data", description = "Retorna registros alimentares de um paciente em uma data específica")
    public ResponseEntity<List<MealLog>> getMealLogsByDate(
            @PathVariable Long patientId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MealLog> logs = nutritionService.findMealLogsByPatientAndDate(patientId, date);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/meal-logs/patient/{patientId}/date-range")
    @Operation(summary = "Listar registros por período", description = "Retorna registros alimentares de um paciente em um período")
    public ResponseEntity<List<MealLog>> getMealLogsByDateRange(
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<MealLog> logs = nutritionService.findMealLogsByPatientAndDateRange(patientId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }
    
    // FoodItem endpoints
    @PostMapping("/foods")
    @Operation(summary = "Criar alimento", description = "Cria um novo alimento na base TACO")
    public ResponseEntity<?> createFoodItem(@RequestBody FoodItem foodItem) {
        try {
            FoodItem createdFood = nutritionService.createFoodItem(foodItem);
            return ResponseEntity.ok(createdFood);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/foods/{foodId}")
    @Operation(summary = "Buscar alimento", description = "Retorna um alimento específico")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long foodId) {
        try {
            FoodItem food = nutritionService.findFoodItemById(foodId);
            return ResponseEntity.ok(food);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/foods/search")
    @Operation(summary = "Buscar alimentos por nome", description = "Busca alimentos por nome")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@RequestParam String name) {
        List<FoodItem> foods = nutritionService.searchFoodItemsByName(name);
        return ResponseEntity.ok(foods);
    }
    
    @GetMapping("/foods/calories")
    @Operation(summary = "Buscar alimentos por faixa calórica", description = "Retorna alimentos dentro de uma faixa calórica")
    public ResponseEntity<List<FoodItem>> getFoodsByCalorieRange(
            @RequestParam Double minKcal,
            @RequestParam Double maxKcal) {
        List<FoodItem> foods = nutritionService.findFoodItemsByCalorieRange(minKcal, maxKcal);
        return ResponseEntity.ok(foods);
    }
    
    @GetMapping("/foods/high-protein")
    @Operation(summary = "Buscar alimentos ricos em proteína", description = "Retorna alimentos com alto teor proteico")
    public ResponseEntity<List<FoodItem>> getHighProteinFoods(@RequestParam Double minProtein) {
        List<FoodItem> foods = nutritionService.findHighProteinFoods(minProtein);
        return ResponseEntity.ok(foods);
    }
    
    @GetMapping("/foods/low-carb")
    @Operation(summary = "Buscar alimentos low carb", description = "Retorna alimentos com baixo teor de carboidratos")
    public ResponseEntity<List<FoodItem>> getLowCarbFoods(@RequestParam Double maxCarbs) {
        List<FoodItem> foods = nutritionService.findLowCarbFoods(maxCarbs);
        return ResponseEntity.ok(foods);
    }
    
    // Utility endpoints
    @GetMapping("/calculate/{foodId}")
    @Operation(summary = "Calcular valores nutricionais", description = "Calcula valores nutricionais para uma quantidade específica")
    public ResponseEntity<?> calculateNutritionalValues(
            @PathVariable Long foodId,
            @RequestParam Double quantityG) {
        try {
            FoodItem food = nutritionService.findFoodItemById(foodId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("food", food);
            response.put("quantityG", quantityG);
            response.put("calories", nutritionService.calculateCalories(food, quantityG));
            response.put("protein", nutritionService.calculateProtein(food, quantityG));
            response.put("carbs", nutritionService.calculateCarbs(food, quantityG));
            response.put("fat", nutritionService.calculateFat(food, quantityG));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 