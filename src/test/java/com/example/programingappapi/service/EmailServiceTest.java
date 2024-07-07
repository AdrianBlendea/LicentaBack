package com.example.programingappapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("test@example.com");
        email.setSubject("Test Subject");
        email.setText("Test Body");

        emailService.sendEmail(email);

        verify(javaMailSender, times(1)).send(email);
    }
}
