package com.caden.fitnessapp.fullStacked.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caden.fitnessapp.fullStacked.Repository.ExerciseRepository;
import com.caden.fitnessapp.fullStacked.Repository.UserRepository;
import com.caden.fitnessapp.fullStacked.Repository.WorkoutRepository;
import com.caden.fitnessapp.fullStacked.dto.ExerciseRequest;
import com.caden.fitnessapp.fullStacked.dto.ExerciseResponse;
import com.caden.fitnessapp.fullStacked.dto.WorkoutRequest;
import com.caden.fitnessapp.fullStacked.dto.WorkoutResponse;
import com.caden.fitnessapp.fullStacked.model.Exercise;
import com.caden.fitnessapp.fullStacked.model.User;
import com.caden.fitnessapp.fullStacked.model.Workout;

@RestController

@RequestMapping("/workouts")
public class WorkoutController{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @PostMapping
    public ResponseEntity<String> createWorkout(@RequestBody WorkoutRequest workoutRequest){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = new Workout();
        workout.setDate(workoutRequest.getDate());
        workout.setWorkout(workoutRequest.getWorkout());
        workout.setUser(user);

        workoutRepository.save(workout);

        return ResponseEntity.ok("Workout created succsesfully");
    }

    @PostMapping("/{workoutId}/exercises")
    public ResponseEntity<String> addExercise(@PathVariable Long workoutId , @RequestBody ExerciseRequest exerciseRequest){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        Workout workout = workoutRepository.findById(workoutId)
        .orElseThrow(() -> new RuntimeException("Workout not found"));


        if (!workout.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to modify this workout");
        }

        Exercise exercise = new Exercise();
        exercise.setReps(exerciseRequest.getReps());
        exercise.setWeight(exerciseRequest.getWeight());
        exercise.setExercise(exerciseRequest.getExercise());
        exercise.setSets(exerciseRequest.getSets());
        exercise.setWorkout(workout);

        exerciseRepository.save(exercise);

        return ResponseEntity.ok("Exercise created succesfully");
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Workout> workouts = workoutRepository.findByUserId(user.getId());
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponse> getWorkoutById(@PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Workout not found"));

        if (!workout.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ExerciseResponse> exerciseResponses = workout.getExercises().stream()
            .map(e -> new ExerciseResponse(e.getExercise(), e.getSets(), e.getReps(), e.getWeight()))
            .toList();
        
        WorkoutResponse response = new WorkoutResponse(workout.getWorkout(), workout.getDate(), exerciseResponses);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Workout not found"));
        
        if (!workout.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete this workout");
        }
        
        workoutRepository.delete(workout);

        return ResponseEntity.ok("Workout deleted succesfully");
    }


    @DeleteMapping("/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<String> deleteExercise(@PathVariable Long id, @PathVariable Long exerciseId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Workout not found"));
        
        if (!workout.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete this workout");
        }
        
        Exercise exercise = exerciseRepository.findById(exerciseId)
        .orElseThrow(() -> new RuntimeException("Exercise not found"));

        
        if (!exercise.getWorkout().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exercise does not belong to this workout");
        }
        

        return ResponseEntity.ok("Exercise deleted succesfully");
    }

    @GetMapping("/progress/{workoutName}")
    public ResponseEntity<List<ExerciseResponse>> getProgress(@PathVariable String name){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<Exercise> exercises = exerciseRepository.findByWorkoutUserAndExercise(user, name);
        
        List<ExerciseResponse> response = exercises.stream()
        .map(e -> new ExerciseResponse(e.getExercise(), e.getSets(), e.getReps(), e.getWeight()))
        .toList();

        return ResponseEntity.ok(response);
    }
}
