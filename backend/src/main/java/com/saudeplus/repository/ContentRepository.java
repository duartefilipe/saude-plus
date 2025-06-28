package com.saudeplus.repository;

import com.saudeplus.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    
    List<Content> findByType(Content.ContentType type);
    
    List<Content> findByVisibility(Content.Visibility visibility);
    
    List<Content> findByAuthorId(Long authorId);
    
    List<Content> findByTypeAndVisibility(Content.ContentType type, Content.Visibility visibility);
    
    List<Content> findByAuthorIdAndType(Long authorId, Content.ContentType type);
    
    List<Content> findByTitleContainingIgnoreCase(String title);
    
    List<Content> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Content> findByVisibilityOrderByCreatedAtDesc(Content.Visibility visibility);
} 