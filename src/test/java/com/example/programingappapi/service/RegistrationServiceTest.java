package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.entity.ConfirmationToken;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.repository.ConfirmationTokenRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("Test User");
        userCreateDTO.setEmail("test@example.com");
        userCreateDTO.setPassword("password");

        UserAccount userAccount = UserAccount.builder()
                .name(userCreateDTO.getName())
                .email(userCreateDTO.getEmail())
                .password("encodedPassword")
                .role("user")
                .enabled(false)
                .build();

        String token = Jwts.builder()
                .setSubject(userAccount.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS512, "7ZOkGijejRONJNeo8jQRJJrrRrIULBXpOfbXvkx4aYrfLczomfyWH8LGcvaPdsX4SG6saCWuZbhYEvovqoErQA==")
                .compact();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccount);
        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(new ConfirmationToken());

        registrationService.registerUser(userCreateDTO);

        verify(userAccountRepository, times(1)).save(any(UserAccount.class));
        verify(confirmationTokenRepository, times(1)).save(any(ConfirmationToken.class));
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));
    }

    @Test
    void testSendConfirmationEmail() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("test@example.com");

        String token = "test-token";

        registrationService.sendConfirmationEmail(userCreateDTO, token);

        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));
    }

    @Test
    void testConfirmUser_Success() {
        String token = "test-token";
        UserAccount userAccount = new UserAccount();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdDate(new Date())
                .userAccount(userAccount)
                .build();

        when(confirmationTokenRepository.findByToken(token)).thenReturn(confirmationToken);

        boolean result = registrationService.confirmUser(token);

        assertTrue(result);
        assertTrue(userAccount.isEnabled());
    }

    @Test
    void testConfirmUser_TokenNotFound() {
        String token = "invalid-token";

        when(confirmationTokenRepository.findByToken(token)).thenReturn(null);

        boolean result = registrationService.confirmUser(token);

        assertFalse(result);
    }
}
