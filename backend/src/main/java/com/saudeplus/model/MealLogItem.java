package com.saudeplus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_log_items")
public class MealLogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_log_id", nullable = false)
    private MealLog mealLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    @Column(nullable = false)
    private Double quantityG;

    @Column(nullable = false)
    private Double kcalCalculated;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MealLog getMealLog() { return mealLog; }
    public void setMealLog(MealLog mealLog) { this.mealLog = mealLog; }
    public FoodItem getFoodItem() { return foodItem; }
    public void setFoodItem(FoodItem foodItem) { this.foodItem = foodItem; }
    public Double getQuantityG() { return quantityG; }
    public void setQuantityG(Double quantityG) { this.quantityG = quantityG; }
    public Double getKcalCalculated() { return kcalCalculated; }
    public void setKcalCalculated(Double kcalCalculated) { this.kcalCalculated = kcalCalculated; }
} 