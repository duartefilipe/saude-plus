package com.saudeplus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "body_metric_templates")
public class BodyMetricTemplate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    private User professional;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name; // ex: "Avaliação Inicial", "Foco em Gordura Abdominal"
    
    @Size(max = 500)
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BodyMetricTemplateField> fields = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public void addField(BodyMetricTemplateField field) {
        fields.add(field);
        field.setTemplate(this);
    }
    
    public void removeField(BodyMetricTemplateField field) {
        fields.remove(field);
        field.setTemplate(null);
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getProfessional() {
        return professional;
    }
    
    public void setProfessional(User professional) {
        this.professional = professional;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<BodyMetricTemplateField> getFields() {
        return fields;
    }
    
    public void setFields(List<BodyMetricTemplateField> fields) {
        this.fields = fields;
    }
} 