package com.saudeplus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double kcalPer100g;
    @Column(nullable = false)
    private Double proteinPer100g;
    @Column(nullable = false)
    private Double carbsPer100g;
    @Column(nullable = false)
    private Double fatPer100g;
    @Column(nullable = false)
    private Double fiberPer100g;
    @Column(nullable = false)
    private Double sodiumPer100g;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getKcalPer100g() { return kcalPer100g; }
    public void setKcalPer100g(Double kcalPer100g) { this.kcalPer100g = kcalPer100g; }
    public Double getProteinPer100g() { return proteinPer100g; }
    public void setProteinPer100g(Double proteinPer100g) { this.proteinPer100g = proteinPer100g; }
    public Double getCarbsPer100g() { return carbsPer100g; }
    public void setCarbsPer100g(Double carbsPer100g) { this.carbsPer100g = carbsPer100g; }
    public Double getFatPer100g() { return fatPer100g; }
    public void setFatPer100g(Double fatPer100g) { this.fatPer100g = fatPer100g; }
    public Double getFiberPer100g() { return fiberPer100g; }
    public void setFiberPer100g(Double fiberPer100g) { this.fiberPer100g = fiberPer100g; }
    public Double getSodiumPer100g() { return sodiumPer100g; }
    public void setSodiumPer100g(Double sodiumPer100g) { this.sodiumPer100g = sodiumPer100g; }
} 