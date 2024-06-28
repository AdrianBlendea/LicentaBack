package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.entity.ConfirmationToken;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.repository.ConfirmationTokenRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class RegistrationService {


    private final UserAccountRepository userAccountRepository;


    private final ConfirmationTokenRepository confirmationTokenRepository;


    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder ;


    private final String SECRET_KEY = "7ZOkGijejRONJNeo8jQRJJrrRrIULBXpOfbXvkx4aYrfLczomfyWH8LGcvaPdsX4SG6saCWuZbhYEvovqoErQA==";

    public void registerUser(UserCreateDTO userCreateDTO) {

        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        UserAccount  userAccount = UserAccount.builder().name(userCreateDTO.getName()).email(userCreateDTO.getEmail())
                .password(encodedPassword).role("user").enabled(false).build();
        userAccountRepository.save(userAccount);

        String token = Jwts.builder()
                .setSubject(userAccount.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdDate(new Date())
                .userAccount(userAccount)
                .build();

        confirmationTokenRepository.save(confirmationToken);

        sendConfirmationEmail(userCreateDTO, token);
    }

    public void sendConfirmationEmail(UserCreateDTO userAccount, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userAccount.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("adrianblendea92@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/confirm-account?token=" + token);

        emailService.sendEmail(mailMessage);
    }
    @Transactional
    public boolean confirmUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken != null && confirmationToken.getUserAccount() != null) {

            UserAccount  userAccount = confirmationToken.getUserAccount();
            userAccount.setEnabled(true);
            return true;
        }

        return false;
    }
}
