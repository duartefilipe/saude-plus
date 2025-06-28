package com.saudeplus.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meal_logs")
public class MealLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String turn;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "mealLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealLogItem> items = new ArrayList<>();

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getTurn() { return turn; }
    public void setTurn(String turn) { this.turn = turn; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<MealLogItem> getItems() { return items; }
    public void setItems(List<MealLogItem> items) { this.items = items; }
} 