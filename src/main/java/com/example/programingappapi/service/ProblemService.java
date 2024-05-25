package com.example.programingappapi.service;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.mapper.ProblemMapper;
import com.example.programingappapi.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final ProblemMapper problemMapper;

    public List<ProblemDTO> getAllExercises()
    {
        return problemMapper.toProblemDtos(problemRepository.findAll());
    }

}
