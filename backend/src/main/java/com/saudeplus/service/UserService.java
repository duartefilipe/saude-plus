package com.saudeplus.service;

import com.saudeplus.model.User;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        // Validar se email já existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + user.getEmail());
        }
        
        // Criptografar senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
        
        // Atualizar campos permitidos
        user.setName(userDetails.getName());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        user.setEnabled(userDetails.isEnabled());
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }
    
    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> findActiveUsers() {
        return userRepository.findByEnabled(true);
    }
    
    public void deleteUser(Long id) {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
} 