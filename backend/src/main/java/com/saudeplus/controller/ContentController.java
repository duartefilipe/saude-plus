package com.saudeplus.controller;

import com.saudeplus.model.Content;
import com.saudeplus.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
@Tag(name = "Conteúdo", description = "Endpoints para gerenciar conteúdos educativos")
@CrossOrigin(origins = "*")
public class ContentController {
    
    @Autowired
    private ContentService contentService;
    
    @PostMapping
    @Operation(summary = "Criar conteúdo", description = "Cria um novo conteúdo educativo")
    public ResponseEntity<?> createContent(@RequestBody Content content) {
        try {
            Content createdContent = contentService.createContent(content);
            return ResponseEntity.ok(createdContent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{contentId}")
    @Operation(summary = "Buscar conteúdo", description = "Retorna um conteúdo específico")
    public ResponseEntity<Content> getContentById(@PathVariable Long contentId) {
        try {
            Content content = contentService.findContentById(contentId);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/public")
    @Operation(summary = "Listar conteúdos públicos", description = "Retorna lista de conteúdos públicos")
    public ResponseEntity<List<Content>> getPublicContents() {
        List<Content> contents = contentService.findPublicContent();
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/type/{contentType}")
    @Operation(summary = "Listar conteúdos por tipo", description = "Retorna conteúdos por tipo (ARTICLE, VIDEO, PODCAST, INFOGRAFIC)")
    public ResponseEntity<List<Content>> getContentsByType(@PathVariable String contentType) {
        try {
            Content.ContentType type = Content.ContentType.valueOf(contentType.toUpperCase());
            List<Content> contents = contentService.findContentByType(type);
            return ResponseEntity.ok(contents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/type/{contentType}/visibility/{visibility}")
    @Operation(summary = "Listar conteúdos por tipo e visibilidade", description = "Retorna conteúdos por tipo e visibilidade")
    public ResponseEntity<List<Content>> getContentsByTypeAndVisibility(
            @PathVariable String contentType,
            @PathVariable String visibility) {
        try {
            Content.ContentType type = Content.ContentType.valueOf(contentType.toUpperCase());
            Content.Visibility vis = Content.Visibility.valueOf(visibility.toUpperCase());
            List<Content> contents = contentService.findContentByTypeAndVisibility(type, vis);
            return ResponseEntity.ok(contents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/author/{authorId}")
    @Operation(summary = "Listar conteúdos por autor", description = "Retorna conteúdos criados por um profissional")
    public ResponseEntity<List<Content>> getContentsByAuthor(@PathVariable Long authorId) {
        List<Content> contents = contentService.findContentByAuthor(authorId);
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/author/{authorId}/type/{contentType}")
    @Operation(summary = "Listar conteúdos por autor e tipo", description = "Retorna conteúdos de um autor por tipo")
    public ResponseEntity<List<Content>> getContentsByAuthorAndType(
            @PathVariable Long authorId,
            @PathVariable String contentType) {
        try {
            Content.ContentType type = Content.ContentType.valueOf(contentType.toUpperCase());
            List<Content> contents = contentService.findContentByAuthorAndType(authorId, type);
            return ResponseEntity.ok(contents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar conteúdos", description = "Busca conteúdos por título")
    public ResponseEntity<List<Content>> searchContents(@RequestParam String title) {
        List<Content> contents = contentService.searchContentByTitle(title);
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/articles")
    @Operation(summary = "Listar artigos", description = "Retorna todos os artigos")
    public ResponseEntity<List<Content>> getArticles() {
        List<Content> contents = contentService.findArticles();
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/recipes")
    @Operation(summary = "Listar receitas", description = "Retorna todas as receitas")
    public ResponseEntity<List<Content>> getRecipes() {
        List<Content> contents = contentService.findRecipes();
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/workouts")
    @Operation(summary = "Listar treinos", description = "Retorna todos os treinos")
    public ResponseEntity<List<Content>> getWorkouts() {
        List<Content> contents = contentService.findWorkouts();
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/news")
    @Operation(summary = "Listar notícias", description = "Retorna todas as notícias")
    public ResponseEntity<List<Content>> getNews() {
        List<Content> contents = contentService.findNews();
        return ResponseEntity.ok(contents);
    }
    
    @GetMapping("/links")
    @Operation(summary = "Listar links", description = "Retorna todos os links")
    public ResponseEntity<List<Content>> getLinks() {
        List<Content> contents = contentService.findLinks();
        return ResponseEntity.ok(contents);
    }
    
    @PutMapping("/{contentId}")
    @Operation(summary = "Atualizar conteúdo", description = "Atualiza um conteúdo")
    public ResponseEntity<?> updateContent(@PathVariable Long contentId, @RequestBody Content contentDetails) {
        try {
            Content updatedContent = contentService.updateContent(contentId, contentDetails);
            return ResponseEntity.ok(updatedContent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{contentId}")
    @Operation(summary = "Deletar conteúdo", description = "Remove um conteúdo")
    public ResponseEntity<?> deleteContent(@PathVariable Long contentId) {
        try {
            contentService.deleteContent(contentId);
            return ResponseEntity.ok().body("Conteúdo removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 