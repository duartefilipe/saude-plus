package com.saudeplus.repository;

import com.saudeplus.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    List<Exercise> findByTrainingPlanId(Long trainingPlanId);
    
    List<Exercise> findByNameContainingIgnoreCase(String name);
    
    List<Exercise> findBySetsGreaterThanEqual(Integer minSets);
    
    List<Exercise> findByRepsGreaterThanEqual(Integer minReps);
} 