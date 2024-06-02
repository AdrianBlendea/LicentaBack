package com.example.programingappapi.controller;

import com.example.programingappapi.dto.TestCaseDTO;
import com.example.programingappapi.entity.TestCase;
import com.example.programingappapi.service.TestCaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestCaseController {
    private final TestCaseService testCaseService;
    @GetMapping("/{id}")
    public ResponseEntity<List<TestCaseDTO>> getTestsForProblem(@PathVariable("id") Long problemId)
    {
        return ResponseEntity.ok(testCaseService.getAllTestsForProblem(problemId));
    }
}
