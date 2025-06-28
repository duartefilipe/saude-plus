package com.saudeplus.controller;

import com.saudeplus.model.ProfessionalNote;
import com.saudeplus.service.ProfessionalNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/professional-notes")
@Tag(name = "Notas Profissionais", description = "Endpoints para gerenciar notas profissionais")
@CrossOrigin(origins = "*")
public class ProfessionalNoteController {
    
    @Autowired
    private ProfessionalNoteService professionalNoteService;
    
    @PostMapping
    @Operation(summary = "Criar nota profissional", description = "Cria uma nova nota profissional")
    public ResponseEntity<?> createProfessionalNote(@RequestBody ProfessionalNote note) {
        try {
            ProfessionalNote createdNote = professionalNoteService.createNote(note);
            return ResponseEntity.ok(createdNote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{noteId}")
    @Operation(summary = "Buscar nota profissional", description = "Retorna uma nota profissional específica")
    public ResponseEntity<ProfessionalNote> getProfessionalNoteById(@PathVariable Long noteId) {
        try {
            ProfessionalNote note = professionalNoteService.findNoteById(noteId);
            return ResponseEntity.ok(note);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Listar notas por paciente", description = "Retorna notas profissionais de um paciente")
    public ResponseEntity<List<ProfessionalNote>> getNotesByPatient(@PathVariable Long patientId) {
        List<ProfessionalNote> notes = professionalNoteService.findNotesByPatient(patientId);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/professional/{professionalId}")
    @Operation(summary = "Listar notas por profissional", description = "Retorna notas criadas por um profissional")
    public ResponseEntity<List<ProfessionalNote>> getNotesByProfessional(@PathVariable Long professionalId) {
        List<ProfessionalNote> notes = professionalNoteService.findNotesByProfessional(professionalId);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/professional/{professionalId}/patient/{patientId}")
    @Operation(summary = "Listar notas por profissional e paciente", description = "Retorna notas de um profissional para um paciente específico")
    public ResponseEntity<List<ProfessionalNote>> getNotesByProfessionalAndPatient(
            @PathVariable Long professionalId,
            @PathVariable Long patientId) {
        List<ProfessionalNote> notes = professionalNoteService.findNotesByProfessionalAndPatient(professionalId, patientId);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/professional/{professionalId}/patient/{patientId}/for-patient")
    @Operation(summary = "Listar notas por profissional para paciente", description = "Retorna notas de um profissional para um paciente específico")
    public ResponseEntity<List<ProfessionalNote>> getNotesByProfessionalForPatient(
            @PathVariable Long professionalId,
            @PathVariable Long patientId) {
        List<ProfessionalNote> notes = professionalNoteService.findNotesByProfessionalForPatient(professionalId, patientId);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/date-range")
    @Operation(summary = "Listar notas por período", description = "Retorna notas em um período específico")
    public ResponseEntity<List<ProfessionalNote>> getNotesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ProfessionalNote> notes = professionalNoteService.findNotesByDateRange(startDate, endDate);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/professional/{professionalId}/count")
    @Operation(summary = "Contar notas por profissional", description = "Retorna o número de notas criadas por um profissional")
    public ResponseEntity<Integer> countNotesByProfessional(@PathVariable Long professionalId) {
        int count = professionalNoteService.countNotesByProfessional(professionalId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/patient/{patientId}/count")
    @Operation(summary = "Contar notas por paciente", description = "Retorna o número de notas de um paciente")
    public ResponseEntity<Integer> countNotesByPatient(@PathVariable Long patientId) {
        int count = professionalNoteService.countNotesByPatient(patientId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/{noteId}/can-access/{userId}")
    @Operation(summary = "Verificar acesso à nota", description = "Verifica se um usuário pode acessar uma nota")
    public ResponseEntity<Boolean> canAccessNote(@PathVariable Long noteId, @PathVariable Long userId) {
        boolean canAccess = professionalNoteService.canAccessNote(noteId, userId);
        return ResponseEntity.ok(canAccess);
    }
    
    @PutMapping("/{noteId}")
    @Operation(summary = "Atualizar nota profissional", description = "Atualiza uma nota profissional")
    public ResponseEntity<?> updateProfessionalNote(@PathVariable Long noteId, @RequestBody ProfessionalNote noteDetails) {
        try {
            ProfessionalNote updatedNote = professionalNoteService.updateNote(noteId, noteDetails);
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{noteId}")
    @Operation(summary = "Deletar nota profissional", description = "Remove uma nota profissional")
    public ResponseEntity<?> deleteProfessionalNote(@PathVariable Long noteId) {
        try {
            professionalNoteService.deleteNote(noteId);
            return ResponseEntity.ok().body("Nota removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 