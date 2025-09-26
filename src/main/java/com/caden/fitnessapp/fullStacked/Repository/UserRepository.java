package com.caden.fitnessapp.fullStacked.Repository;
//imports user class to the database 
import com.caden.fitnessapp.fullStacked.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Creates userRepository interface to store user objects 
//The primary key of a user is a long
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
