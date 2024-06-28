package com.example.programingappapi.controller;

import com.example.programingappapi.dto.ProblemCreateDTO;
import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.service.ProblemService;
import jakarta.annotation.Nullable;
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


    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable("id") String id, @RequestParam Long userId) {
        return ResponseEntity.ok(problemService.getProblemById(id, userId));
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<List<ProblemDTO>> getAllProblemsByType(@PathVariable("id") Long typeId,
                                                                 @RequestParam
                                                                 @Nullable Long userId) {
        return ResponseEntity.ok(problemService.getAllByType(typeId, userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProblem(@RequestParam("problemId")Long problemId)
    {
        return ResponseEntity.ok(problemService.deleteProblem(problemId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProblem (@RequestBody ProblemCreateDTO problemCreateDTO)
    {
        return ResponseEntity.ok(problemService.createProblem(problemCreateDTO));
    }


}
