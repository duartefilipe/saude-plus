package com.saudeplus.controller;

import com.saudeplus.model.User;
import com.saudeplus.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @GetMapping("/test")
    @Operation(summary = "Teste simples", description = "Endpoint de teste simples")
    public ResponseEntity<?> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Teste funcionando!");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica usuário e retorna dados do usuário")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("message", "Login realizado com sucesso");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User createdUser = authService.registerUser(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", createdUser);
            response.put("message", "Usuário registrado com sucesso");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // DTO para requisição de login
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    // DTO para requisição de registro
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private User.UserRole role;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public User.UserRole getRole() { return role; }
        public void setRole(User.UserRole role) { this.role = role; }
    }
} 