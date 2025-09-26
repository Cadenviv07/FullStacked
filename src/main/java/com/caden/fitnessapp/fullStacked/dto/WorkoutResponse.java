package com.caden.fitnessapp.fullStacked.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutResponse {
    private String name;
    private LocalDate date;
    private List<ExerciseResponse> exercises;

    public WorkoutResponse(String name, LocalDate date, List<ExerciseResponse> exercises) {
        this.name = name;
        this.date = date;
        this.exercises = exercises;
    }

    // Getters
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public List<ExerciseResponse> getExercises() { return exercises; }
}
