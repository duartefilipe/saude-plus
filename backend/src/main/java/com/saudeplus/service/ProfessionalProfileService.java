package com.saudeplus.service;

import com.saudeplus.model.ProfessionalProfile;
import com.saudeplus.model.User;
import com.saudeplus.repository.ProfessionalProfileRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfessionalProfileService {
    
    @Autowired
    private ProfessionalProfileRepository professionalProfileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public ProfessionalProfile createProfile(ProfessionalProfile profile) {
        // Validar se o usuário existe e é um profissional
        User user = userRepository.findById(profile.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (user.getRole() != User.UserRole.PROFESSIONAL) {
            throw new RuntimeException("Apenas profissionais podem ter perfis profissionais");
        }
        
        // Validar se já existe um perfil para este usuário
        if (professionalProfileRepository.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("Usuário já possui um perfil profissional");
        }
        
        profile.setUser(user);
        return professionalProfileRepository.save(profile);
    }
    
    public ProfessionalProfile updateProfile(Long id, ProfessionalProfile profileDetails) {
        ProfessionalProfile profile = professionalProfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil profissional não encontrado: " + id));
        
        profile.setSpecialty(profileDetails.getSpecialty());
        profile.setRegistrationNumber(profileDetails.getRegistrationNumber());
        profile.setBio(profileDetails.getBio());
        profile.setProfileImageUrl(profileDetails.getProfileImageUrl());
        profile.setActive(profileDetails.isActive());
        
        return professionalProfileRepository.save(profile);
    }
    
    public ProfessionalProfile findById(Long id) {
        return professionalProfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil profissional não encontrado: " + id));
    }
    
    public Optional<ProfessionalProfile> findByUserId(Long userId) {
        return professionalProfileRepository.findByUserId(userId);
    }
    
    public List<ProfessionalProfile> findBySpecialty(String specialty) {
        return professionalProfileRepository.findBySpecialty(specialty);
    }
    
    public List<ProfessionalProfile> findActiveProfessionals() {
        return professionalProfileRepository.findByActive(true);
    }
    
    public List<ProfessionalProfile> findVerifiedProfessionals() {
        return professionalProfileRepository.findByVerified(true);
    }
    
    public void verifyProfile(Long id) {
        ProfessionalProfile profile = findById(id);
        profile.setVerified(true);
        professionalProfileRepository.save(profile);
    }
    
    public void deactivateProfile(Long id) {
        ProfessionalProfile profile = findById(id);
        profile.setActive(false);
        professionalProfileRepository.save(profile);
    }
} 