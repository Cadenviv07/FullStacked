package com.caden.fitnessapp.fullStacked.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//Maps user class to database table named user
@Entity
@Table(name = "app_user")

public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts = new ArrayList<>();
    
    //Primary key of table
    @Id
    //Randomly generates id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Ensures all usernames are unique
    @Column(unique = true)
    private String username;


    private String password;
    private String role; // e.g. ROLE_USER or ROLE_ADMIN
    private String email;
    private boolean isEnabled;

    //get id 
    public Long getId(){return id;}

    //get username 
    public String getUsername(){return username;}
    //set username
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    //get password
    public String getPassword(){return password;}
    //set password
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled(){return isEnabled;}

    public void setEnable(boolean enable){
        this.isEnabled = enable;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role;}
    
}
