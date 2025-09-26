package com.caden.fitnessapp.fullStacked.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.caden.fitnessapp.fullStacked.Repository.UserRepository;
import com.caden.fitnessapp.fullStacked.Repository.VerificationCodeRepository;
import com.caden.fitnessapp.fullStacked.model.User;
import com.caden.fitnessapp.fullStacked.model.VerificationCode;

@Service
public class VerificationService {
    
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public void createVerificationCode(User user){
        String code = String.format("%06d", new Random().nextInt(999999));
        
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(code);
        verificationCode.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        verificationCodeRepository.save(verificationCode);

        sendVerifcationEmail(user.getEmail(), code);
    }

    private void sendVerifcationEmail(String toEmail, String code){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(" Email Verification Code");
        message.setText("Your verification code is: " + code);
        
        javaMailSender.send(message);
    }

    public boolean verifyCode(String email, String code){
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) return false;

        User user = userOpt.get();

        Optional<VerificationCode> codeOpt = verificationCodeRepository.findByCode(code);

        if(codeOpt.isEmpty()) return false;

        VerificationCode verificationCode = codeOpt.get();

        if (!verificationCode.getCode().equals(code)) return false;

        if (verificationCode.getExpiryDate().isBefore(LocalDateTime.now())) return false;

        user.setEnable(true);
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);

        return true;

    }

}
