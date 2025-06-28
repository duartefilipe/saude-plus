package com.saudeplus.controller;

import com.saudeplus.model.User;
import com.saudeplus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna lista de todos os usuários ativos")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findActiveUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/role/{role}")
    @Operation(summary = "Buscar usuários por role", description = "Retorna usuários por tipo (ADMIN, PROFESSIONAL, PATIENT)")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        try {
            User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
            List<User> users = userService.findByRole(userRole);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza dados de um usuário")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Desabilitar usuário", description = "Desabilita um usuário (soft delete)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("Usuário desabilitado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um usuário pelo email")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
} 