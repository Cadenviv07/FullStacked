package com.caden.fitnessapp.fullStacked.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caden.fitnessapp.fullStacked.model.User;
import com.caden.fitnessapp.fullStacked.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    
    Optional<VerificationCode> findByCode(String token);
    Optional<User> findByUser_Username(String username);
}
