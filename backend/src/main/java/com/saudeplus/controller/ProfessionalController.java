package com.saudeplus.controller;

import com.saudeplus.model.ProfessionalProfile;
import com.saudeplus.service.ProfessionalProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professionals")
@Tag(name = "Profissionais", description = "Endpoints para gerenciamento de perfis profissionais")
@CrossOrigin(origins = "*")
public class ProfessionalController {
    
    @Autowired
    private ProfessionalProfileService professionalProfileService;
    
    @PostMapping
    @Operation(summary = "Criar perfil profissional", description = "Cria um novo perfil profissional")
    public ResponseEntity<?> createProfile(@RequestBody ProfessionalProfile profile) {
        try {
            ProfessionalProfile createdProfile = professionalProfileService.createProfile(profile);
            return ResponseEntity.ok(createdProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar profissionais ativos", description = "Retorna lista de profissionais ativos")
    public ResponseEntity<List<ProfessionalProfile>> getActiveProfessionals() {
        List<ProfessionalProfile> professionals = professionalProfileService.findActiveProfessionals();
        return ResponseEntity.ok(professionals);
    }
    
    @GetMapping("/verified")
    @Operation(summary = "Listar profissionais verificados", description = "Retorna lista de profissionais verificados")
    public ResponseEntity<List<ProfessionalProfile>> getVerifiedProfessionals() {
        List<ProfessionalProfile> professionals = professionalProfileService.findVerifiedProfessionals();
        return ResponseEntity.ok(professionals);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar profissional por ID", description = "Retorna um perfil profissional específico")
    public ResponseEntity<ProfessionalProfile> getProfessionalById(@PathVariable Long id) {
        try {
            ProfessionalProfile profile = professionalProfileService.findById(id);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar profissional por usuário", description = "Retorna perfil profissional pelo ID do usuário")
    public ResponseEntity<ProfessionalProfile> getProfessionalByUserId(@PathVariable Long userId) {
        return professionalProfileService.findByUserId(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/specialty/{specialty}")
    @Operation(summary = "Buscar profissionais por especialidade", description = "Retorna profissionais por especialidade")
    public ResponseEntity<List<ProfessionalProfile>> getProfessionalsBySpecialty(@PathVariable String specialty) {
        List<ProfessionalProfile> professionals = professionalProfileService.findBySpecialty(specialty);
        return ResponseEntity.ok(professionals);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil profissional", description = "Atualiza dados de um perfil profissional")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody ProfessionalProfile profileDetails) {
        try {
            ProfessionalProfile updatedProfile = professionalProfileService.updateProfile(id, profileDetails);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/verify")
    @Operation(summary = "Verificar profissional", description = "Marca um profissional como verificado")
    public ResponseEntity<?> verifyProfessional(@PathVariable Long id) {
        try {
            professionalProfileService.verifyProfile(id);
            return ResponseEntity.ok().body("Profissional verificado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Desativar profissional", description = "Desativa um perfil profissional")
    public ResponseEntity<?> deactivateProfessional(@PathVariable Long id) {
        try {
            professionalProfileService.deactivateProfile(id);
            return ResponseEntity.ok().body("Profissional desativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 