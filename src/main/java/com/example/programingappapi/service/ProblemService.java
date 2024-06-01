package com.example.programingappapi.service;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.exception.ProblemNotFoundException;
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

    public List<ProblemDTO> getAllProblems() {
        return problemMapper.toProblemDtos(problemRepository.findAll());
    }

    public ProblemDTO getProblemById(String id) {
        Problem problemDTO = problemRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given id not found"));

        return problemMapper.toProblemDTO(problemDTO);
    }

    public List<ProblemDTO> getAllByType(Long typeId) {
        return problemMapper.toProblemDtos(problemRepository.findAllByTypeId(typeId));
    }
}
