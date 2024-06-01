package com.example.programingappapi.controller;

import com.example.programingappapi.dto.TypeDTO;
import com.example.programingappapi.entity.Type;
import com.example.programingappapi.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/type")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TypeController {
    private final TypeService typeService;
    @GetMapping("/all")
    public ResponseEntity<List<TypeDTO>> getAllTypes()
    {
        return ResponseEntity.ok(typeService.getAllTypes());
    }

}
