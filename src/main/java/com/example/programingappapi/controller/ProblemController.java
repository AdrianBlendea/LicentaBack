package com.example.programingappapi.controller;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.service.ProblemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/all")
    public ResponseEntity<List<ProblemDTO>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable("id") String id) {
        return ResponseEntity.ok(problemService.getProblemById(id));
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<List<ProblemDTO>> getAllProblemsByType(@PathVariable("id") Long typeId) {
        return ResponseEntity.ok(problemService.getAllByType(typeId));
    }

}
