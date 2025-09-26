package com.caden.fitnessapp.fullStacked.dto;

import java.time.LocalDate;

public class WorkoutRequest {
    private String name;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWorkout(){
        return name;
    }

    public void setWorkout(String name){
        this.name = name;
    }

}
