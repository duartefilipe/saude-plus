package com.saudeplus.service;

import com.saudeplus.model.User;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        User user = userOpt.get();
        
        if (!user.isEnabled()) {
            throw new RuntimeException("Usuário desabilitado");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }
        
        return user;
    }
    
    public User registerUser(String name, String email, String password, User.UserRole role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEnabled(true);
        
        return userRepository.save(user);
    }
} 