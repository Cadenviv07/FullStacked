package com.caden.fitnessapp.fullStacked.service;

import com.caden.fitnessapp.fullStacked.model.User;
import com.caden.fitnessapp.fullStacked.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
//Makes class into a service component
@Service
//When someone tries to log in this class tells spring how to fetch a user object from the database and translate it into userdetails
public class CustomUserDetailsService implements UserDetailsService {
    //Creates instance of userRepository
    @Autowired
    private UserRepository userRepository;

    //Calls this method when someone logs in with the username they typed and fetches it from the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //If user is not found throw user not found exception so log in fails
        User user = userRepository.findByUsername(username)
                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Converts user object into a spring security user object
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }    
}
