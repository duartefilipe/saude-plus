package com.saudeplus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_plan_items")
public class MealPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_plan_id", nullable = false)
    private MealPlan mealPlan;

    @Column(nullable = false)
    private String turn; // Ex: café, almoço, jantar

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String quantity;

    @Column
    private String time; // Ex: 08:00

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MealPlan getMealPlan() { return mealPlan; }
    public void setMealPlan(MealPlan mealPlan) { this.mealPlan = mealPlan; }
    public String getTurn() { return turn; }
    public void setTurn(String turn) { this.turn = turn; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
} 