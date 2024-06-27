package com.example.programingappapi.controller;

import com.example.programingappapi.service.PlagiarismService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/plagiarism", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlagiarismController {
    private final PlagiarismService plagiarismService;

    @GetMapping()
    public  ResponseEntity<FileSystemResource> getPlagiarismReport(@RequestParam("problemId") Long problemId, @RequestParam("solutionLanguage") String solutionLanguage)
    {
        return ResponseEntity.ok(new FileSystemResource(plagiarismService.getPlagiarismReport(problemId, solutionLanguage)));

    }
}
