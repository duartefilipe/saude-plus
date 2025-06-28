package com.saudeplus.controller;

import com.saudeplus.model.BodyMetricSession;
import com.saudeplus.model.BodyMetricEntry;
import com.saudeplus.service.BodyMetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/body-metrics")
@Tag(name = "Métricas Corporais", description = "Endpoints para gerenciar métricas corporais")
@CrossOrigin(origins = "*")
public class BodyMetricController {
    
    @Autowired
    private BodyMetricService bodyMetricService;
    
    @PostMapping("/sessions")
    @Operation(summary = "Criar sessão de métricas", description = "Cria uma nova sessão de avaliação corporal")
    public ResponseEntity<?> createSession(@RequestBody BodyMetricSession session) {
        try {
            BodyMetricSession createdSession = bodyMetricService.createSession(session);
            return ResponseEntity.ok(createdSession);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/sessions/{sessionId}")
    @Operation(summary = "Buscar sessão por ID", description = "Retorna uma sessão específica")
    public ResponseEntity<BodyMetricSession> getSessionById(@PathVariable Long sessionId) {
        try {
            BodyMetricSession session = bodyMetricService.findSessionById(sessionId);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/sessions/patient/{patientId}")
    @Operation(summary = "Listar sessões por paciente", description = "Retorna todas as sessões de um paciente")
    public ResponseEntity<List<BodyMetricSession>> getSessionsByPatient(@PathVariable Long patientId) {
        List<BodyMetricSession> sessions = bodyMetricService.findSessionsByPatient(patientId);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/sessions/patient/{patientId}/date-range")
    @Operation(summary = "Listar sessões por período", description = "Retorna sessões de um paciente em um período específico")
    public ResponseEntity<List<BodyMetricSession>> getSessionsByDateRange(
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BodyMetricSession> sessions = bodyMetricService.findSessionsByPatientAndDateRange(patientId, startDate, endDate);
        return ResponseEntity.ok(sessions);
    }
    
    @PostMapping("/sessions/{sessionId}/entries")
    @Operation(summary = "Adicionar entrada à sessão", description = "Adiciona uma nova entrada de métrica à sessão")
    public ResponseEntity<?> addEntryToSession(@PathVariable Long sessionId, @RequestBody BodyMetricEntry entry) {
        try {
            BodyMetricEntry createdEntry = bodyMetricService.addEntryToSession(sessionId, entry);
            return ResponseEntity.ok(createdEntry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/sessions/{sessionId}/entries")
    @Operation(summary = "Listar entradas da sessão", description = "Retorna todas as entradas de uma sessão")
    public ResponseEntity<List<BodyMetricEntry>> getEntriesBySession(@PathVariable Long sessionId) {
        List<BodyMetricEntry> entries = bodyMetricService.findEntriesBySession(sessionId);
        return ResponseEntity.ok(entries);
    }
    
    @PutMapping("/entries/{entryId}")
    @Operation(summary = "Atualizar entrada", description = "Atualiza uma entrada de métrica")
    public ResponseEntity<?> updateEntry(@PathVariable Long entryId, @RequestBody BodyMetricEntry entryDetails) {
        try {
            BodyMetricEntry updatedEntry = bodyMetricService.updateEntry(entryId, entryDetails);
            return ResponseEntity.ok(updatedEntry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/entries/{entryId}")
    @Operation(summary = "Deletar entrada", description = "Remove uma entrada de métrica")
    public ResponseEntity<?> deleteEntry(@PathVariable Long entryId) {
        try {
            bodyMetricService.deleteEntry(entryId);
            return ResponseEntity.ok().body("Entrada removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/entries/name/{metricName}")
    @Operation(summary = "Buscar entradas por nome", description = "Retorna entradas por nome da métrica")
    public ResponseEntity<List<BodyMetricEntry>> getEntriesByName(@PathVariable String metricName) {
        List<BodyMetricEntry> entries = bodyMetricService.findEntriesByName(metricName);
        return ResponseEntity.ok(entries);
    }
    
    @DeleteMapping("/sessions/{sessionId}")
    @Operation(summary = "Deletar sessão", description = "Remove uma sessão completa")
    public ResponseEntity<?> deleteSession(@PathVariable Long sessionId) {
        try {
            bodyMetricService.deleteSession(sessionId);
            return ResponseEntity.ok().body("Sessão removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 