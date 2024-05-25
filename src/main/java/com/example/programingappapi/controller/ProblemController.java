package com.example.programingappapi.controller;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.service.ProblemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
@AllArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/all")
    public ResponseEntity<List<ProblemDTO>> getAllProblems()
    {
        return ResponseEntity.ok(problemService.getAllExercises());
    }


}
