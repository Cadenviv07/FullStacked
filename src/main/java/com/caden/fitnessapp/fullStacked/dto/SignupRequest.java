package com.caden.fitnessapp.fullStacked.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {
   

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;


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
}
