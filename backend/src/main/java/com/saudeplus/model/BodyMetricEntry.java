package com.saudeplus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "body_metric_entries")
public class BodyMetricEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private BodyMetricSession session;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name; // ex: "Peso", "Tríceps", "Circunferência Abdominal"
    
    @Size(max = 20)
    @Column(nullable = false)
    private String unit; // kg, cm, mm, %, etc.
    
    @NotNull
    @Column(nullable = false)
    private Double value;
    
    @Size(max = 50)
    private String position; // opcional, ex: "lado direito", "anterior", etc.
    
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BodyMetricSession getSession() {
        return session;
    }
    
    public void setSession(BodyMetricSession session) {
        this.session = session;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Double getValue() {
        return value;
    }
    
    public void setValue(Double value) {
        this.value = value;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
} 