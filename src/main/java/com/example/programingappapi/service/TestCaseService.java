package com.example.programingappapi.service;

import com.example.programingappapi.dto.TestCaseDTO;
import com.example.programingappapi.mapper.TestCaseMapper;
import com.example.programingappapi.repository.TestCaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestCaseService {

    private final TestCaseMapper testCaseMapper;

    private final TestCaseRepository testCaseRepository;

    public List<TestCaseDTO> getAllTestsForProblem(Long problemId)
    {
        return testCaseRepository.findAllByProblem_Id(problemId).stream()
                .map(testCaseMapper::toTestCaseDTO).collect(Collectors.toList());
    }
}
