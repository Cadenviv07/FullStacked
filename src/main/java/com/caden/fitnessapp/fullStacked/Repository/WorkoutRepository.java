package com.caden.fitnessapp.fullStacked.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caden.fitnessapp.fullStacked.model.User;
import com.caden.fitnessapp.fullStacked.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long>{
    Optional<Workout> findByUser(User user);
    List<Workout> findByUserId(Long userId);
}
