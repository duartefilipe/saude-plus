package com.saudeplus.repository;

import com.saudeplus.model.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {
    
    List<FollowRequest> findBySenderId(Long senderId);
    
    List<FollowRequest> findByReceiverId(Long receiverId);
    
    List<FollowRequest> findBySenderIdAndStatus(Long senderId, FollowRequest.Status status);
    
    List<FollowRequest> findByReceiverIdAndStatus(Long receiverId, FollowRequest.Status status);
    
    Optional<FollowRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
    boolean existsBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, FollowRequest.Status status);
} 