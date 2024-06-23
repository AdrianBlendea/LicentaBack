package com.example.programingappapi.controller;


import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.dto.UserDTO;
import com.example.programingappapi.dto.UserUpdateDTO;
import com.example.programingappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;

  @PostMapping
  ResponseEntity<String> createUserAccount(@RequestBody UserCreateDTO userCreateDTO) {
    return userService.createUser(userCreateDTO);
  }

  @DeleteMapping({"/{id}"})
  ResponseEntity<String> deleteUserAccount(@PathVariable(name = "id") Long id) {
    return userService.deleteUser(id);
  }

  @PatchMapping("/{id}")
  ResponseEntity<String> updateUserAccount(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable(name = "id") Long id)
  {
    return userService.updateUser(userUpdateDTO, id);
  }
  @GetMapping("{id}")
  ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable(name = "id") Long id)
  {
    return userService.getAllUsers(id);
  }



}
