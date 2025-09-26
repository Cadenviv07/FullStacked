package com.caden.fitnessapp.fullStacked.dto;

public class ExerciseResponse {
    private String exercise;
    private int sets;
    private int reps;
    private double weight;

    public ExerciseResponse(String exercise, int sets, int reps, double weight) {
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters
    public String getExercise() { return exercise; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public double getWeight() { return weight; }
}
