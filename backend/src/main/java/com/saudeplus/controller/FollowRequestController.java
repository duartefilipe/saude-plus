package com.saudeplus.controller;

import com.saudeplus.model.FollowRequest;
import com.saudeplus.service.FollowRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow-requests")
@Tag(name = "Solicitações de Acompanhamento", description = "Endpoints para gerenciar solicitações de acompanhamento")
@CrossOrigin(origins = "*")
public class FollowRequestController {
    
    @Autowired
    private FollowRequestService followRequestService;
    
    @PostMapping
    @Operation(summary = "Criar solicitação", description = "Cria uma nova solicitação de acompanhamento")
    public ResponseEntity<?> createRequest(@RequestBody FollowRequest request) {
        try {
            FollowRequest createdRequest = followRequestService.createRequest(request);
            return ResponseEntity.ok(createdRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/pending/{receiverId}")
    @Operation(summary = "Listar solicitações pendentes", description = "Retorna solicitações pendentes de um usuário")
    public ResponseEntity<List<FollowRequest>> getPendingRequests(@PathVariable Long receiverId) {
        List<FollowRequest> requests = followRequestService.findPendingRequestsByReceiver(receiverId);
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/sender/{senderId}")
    @Operation(summary = "Listar solicitações enviadas", description = "Retorna solicitações enviadas por um usuário")
    public ResponseEntity<List<FollowRequest>> getSentRequests(@PathVariable Long senderId) {
        List<FollowRequest> requests = followRequestService.findRequestsBySender(senderId);
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/accepted/{userId}")
    @Operation(summary = "Listar conexões aceitas", description = "Retorna conexões aceitas de um usuário")
    public ResponseEntity<List<FollowRequest>> getAcceptedRequests(@PathVariable Long userId) {
        List<FollowRequest> requests = followRequestService.findAcceptedRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }
    
    @PutMapping("/{requestId}/accept")
    @Operation(summary = "Aceitar solicitação", description = "Aceita uma solicitação de acompanhamento")
    public ResponseEntity<?> acceptRequest(@PathVariable Long requestId) {
        try {
            FollowRequest acceptedRequest = followRequestService.acceptRequest(requestId);
            return ResponseEntity.ok(acceptedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{requestId}/reject")
    @Operation(summary = "Rejeitar solicitação", description = "Rejeita uma solicitação de acompanhamento")
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId) {
        try {
            FollowRequest rejectedRequest = followRequestService.rejectRequest(requestId);
            return ResponseEntity.ok(rejectedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/connection/{user1Id}/{user2Id}")
    @Operation(summary = "Verificar conexão", description = "Verifica se existe conexão ativa entre dois usuários")
    public ResponseEntity<?> checkConnection(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        boolean hasConnection = followRequestService.hasActiveConnection(user1Id, user2Id);
        return ResponseEntity.ok(hasConnection);
    }
    
    @DeleteMapping("/{requestId}")
    @Operation(summary = "Deletar solicitação", description = "Remove uma solicitação de acompanhamento")
    public ResponseEntity<?> deleteRequest(@PathVariable Long requestId) {
        try {
            followRequestService.deleteRequest(requestId);
            return ResponseEntity.ok().body("Solicitação removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 