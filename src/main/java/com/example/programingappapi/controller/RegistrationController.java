package com.example.programingappapi.controller;

import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.repository.UserAccountRepository;
import com.example.programingappapi.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO
                                                       userAccount) {
        if (userAccountRepository.findByEmail(userAccount.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        registrationService.registerUser(userAccount);
        return ResponseEntity.ok("Registration successful! Please check your email to confirm your account.");
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String token) {
        boolean isConfirmed = registrationService.confirmUser(token);

        if (isConfirmed) {
            return ResponseEntity.ok("Account verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}
