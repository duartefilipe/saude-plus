package com.saudeplus.service;

import com.saudeplus.model.FollowRequest;
import com.saudeplus.model.User;
import com.saudeplus.repository.FollowRequestRepository;
import com.saudeplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FollowRequestService {
    
    @Autowired
    private FollowRequestRepository followRequestRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public FollowRequest createRequest(FollowRequest request) {
        // Validar se os usuários existem
        User sender = userRepository.findById(request.getSender().getId())
            .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));
        
        User receiver = userRepository.findById(request.getReceiver().getId())
            .orElseThrow(() -> new RuntimeException("Destinatário não encontrado"));
        
        // Validar se não é a mesma pessoa
        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("Não é possível enviar solicitação para si mesmo");
        }
        
        // Validar se já existe uma solicitação pendente
        if (followRequestRepository.existsBySenderIdAndReceiverIdAndStatus(
                sender.getId(), receiver.getId(), FollowRequest.Status.PENDING)) {
            throw new RuntimeException("Já existe uma solicitação pendente");
        }
        
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FollowRequest.Status.PENDING);
        
        return followRequestRepository.save(request);
    }
    
    public FollowRequest acceptRequest(Long requestId) {
        FollowRequest request = followRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        if (request.getStatus() != FollowRequest.Status.PENDING) {
            throw new RuntimeException("Solicitação não está pendente");
        }
        
        request.setStatus(FollowRequest.Status.ACCEPTED);
        return followRequestRepository.save(request);
    }
    
    public FollowRequest rejectRequest(Long requestId) {
        FollowRequest request = followRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        if (request.getStatus() != FollowRequest.Status.PENDING) {
            throw new RuntimeException("Solicitação não está pendente");
        }
        
        request.setStatus(FollowRequest.Status.REJECTED);
        return followRequestRepository.save(request);
    }
    
    public List<FollowRequest> findPendingRequestsByReceiver(Long receiverId) {
        return followRequestRepository.findByReceiverIdAndStatus(receiverId, FollowRequest.Status.PENDING);
    }
    
    public List<FollowRequest> findRequestsBySender(Long senderId) {
        return followRequestRepository.findBySenderId(senderId);
    }
    
    public List<FollowRequest> findAcceptedRequestsByUser(Long userId) {
        return followRequestRepository.findBySenderIdAndStatus(userId, FollowRequest.Status.ACCEPTED);
    }
    
    public boolean hasActiveConnection(Long user1Id, Long user2Id) {
        return followRequestRepository.existsBySenderIdAndReceiverIdAndStatus(
                user1Id, user2Id, FollowRequest.Status.ACCEPTED) ||
               followRequestRepository.existsBySenderIdAndReceiverIdAndStatus(
                user2Id, user1Id, FollowRequest.Status.ACCEPTED);
    }
    
    public void deleteRequest(Long requestId) {
        FollowRequest request = followRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        followRequestRepository.delete(request);
    }
} 