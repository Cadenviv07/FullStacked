package com.caden.fitnessapp.fullStacked.service;

import com.caden.fitnessapp.fullStacked.Repository.UserRepository;
import com.caden.fitnessapp.fullStacked.model.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class UserService{
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public String verify(User user) {
        
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        User enable = existingUser.get();

        if (!enable.getEnabled()) {
        throw new IllegalStateException("Email not verified");
        }

        Authentication authentication = 
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());

        return "Failure";
    }

}
