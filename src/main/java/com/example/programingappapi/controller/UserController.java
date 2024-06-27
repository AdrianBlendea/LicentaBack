package com.example.programingappapi.controller;


import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.dto.UserDTO;
import com.example.programingappapi.dto.UserUpdateDTO;
import com.example.programingappapi.service.PlagiarismService;
import com.example.programingappapi.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class UserController {

  private final UserService userService;
  private final PlagiarismService plagiarismService;

  @PostMapping("/setPicture")
  public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) throws IOException {
    return ResponseEntity.ok(userService.setProfilePicture(file, userId));
  }

  @GetMapping("/getPicture")
  public ResponseEntity<byte[]> getProfilePicture(@RequestParam("userId") Long userId) throws IOException {
    return ResponseEntity.ok(userService.getProfilePicture(userId));
  }

  @DeleteMapping("/deletePicture")
  public ResponseEntity<String> deleteProfilePicture(@RequestParam("userId") Long userId) throws IOException {
    return ResponseEntity.ok(userService.deleteProfilePicture(userId));
  }



}
