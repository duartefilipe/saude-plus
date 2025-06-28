package com.saudeplus.repository;

import com.saudeplus.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    
    Optional<FoodItem> findByName(String name);
    
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    
    List<FoodItem> findByKcalPer100gBetween(Double minKcal, Double maxKcal);
    
    List<FoodItem> findByProteinPer100gGreaterThan(Double minProtein);
    
    List<FoodItem> findByCarbsPer100gLessThan(Double maxCarbs);
} 