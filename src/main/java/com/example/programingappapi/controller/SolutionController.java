package com.example.programingappapi.controller;

import com.example.programingappapi.dto.SolutionDTO;
import com.example.programingappapi.service.SolutionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/solution")
@CrossOrigin(origins = "*")
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitSolution(@RequestBody SolutionDTO solutionDTO)
    {
        return ResponseEntity.ok(solutionService.submitSolution(solutionDTO));
    }

    @GetMapping()
    public ResponseEntity<SolutionDTO> getSolutionForProblemByUser(@RequestParam("userId")Long userId, @RequestParam("problemId") Long problemId)
    {
        return ResponseEntity.ok(solutionService.getSolutionByUser(userId,problemId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSolutionForUser(@RequestParam("userId")Long userId, @RequestParam("problemId") Long problemId)
    {
        return ResponseEntity.ok(solutionService.deleteSolutionByUser(userId,problemId));
    }
}
