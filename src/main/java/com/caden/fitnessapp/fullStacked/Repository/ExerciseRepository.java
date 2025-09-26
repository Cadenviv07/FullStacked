package com.caden.fitnessapp.fullStacked.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caden.fitnessapp.fullStacked.model.Exercise;
import com.caden.fitnessapp.fullStacked.model.User;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByWorkoutId(Long workoutId);
    List<Exercise> findByWorkoutUserAndExercise(User user, String exercise);
}
