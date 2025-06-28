package com.saudeplus.service;

import com.saudeplus.model.Content;
import com.saudeplus.model.User;
import com.saudeplus.repository.ContentRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ContentService {
    
    @Autowired
    private ContentRepository contentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Content createContent(Content content) {
        User author = userRepository.findById(content.getAuthor().getId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        
        content.setAuthor(author);
        return contentRepository.save(content);
    }
    
    public Content findContentById(Long contentId) {
        return contentRepository.findById(contentId)
            .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));
    }
    
    public List<Content> findPublicContent() {
        return contentRepository.findByVisibilityOrderByCreatedAtDesc(Content.Visibility.PUBLIC);
    }
    
    public List<Content> findContentByType(Content.ContentType type) {
        return contentRepository.findByType(type);
    }
    
    public List<Content> findContentByTypeAndVisibility(Content.ContentType type, Content.Visibility visibility) {
        return contentRepository.findByTypeAndVisibility(type, visibility);
    }
    
    public List<Content> findContentByAuthor(Long authorId) {
        return contentRepository.findByAuthorId(authorId);
    }
    
    public List<Content> findContentByAuthorAndType(Long authorId, Content.ContentType type) {
        return contentRepository.findByAuthorIdAndType(authorId, type);
    }
    
    public List<Content> searchContentByTitle(String title) {
        return contentRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Content> findContentByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return contentRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    public Content updateContent(Long contentId, Content contentDetails) {
        Content content = findContentById(contentId);
        
        content.setTitle(contentDetails.getTitle());
        content.setDescription(contentDetails.getDescription());
        content.setContent(contentDetails.getContent());
        content.setVisibility(contentDetails.getVisibility());
        
        return contentRepository.save(content);
    }
    
    public void deleteContent(Long contentId) {
        Content content = findContentById(contentId);
        contentRepository.delete(content);
    }
    
    // Utility methods for different content types
    public List<Content> findArticles() {
        return contentRepository.findByType(Content.ContentType.ARTICLE);
    }
    
    public List<Content> findRecipes() {
        return contentRepository.findByType(Content.ContentType.RECIPE);
    }
    
    public List<Content> findWorkouts() {
        return contentRepository.findByType(Content.ContentType.WORKOUT);
    }
    
    public List<Content> findNews() {
        return contentRepository.findByType(Content.ContentType.NEWS);
    }
    
    public List<Content> findLinks() {
        return contentRepository.findByType(Content.ContentType.LINK);
    }
} 