package com.example.programingappapi.controller;


import com.example.programingappapi.dto.LoginDTO;
import com.example.programingappapi.dto.UserTokenDTO;
import com.example.programingappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO.getEmail(), loginDTO.getPassword());
    }


}
