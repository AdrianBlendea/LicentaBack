package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.TestCaseCreateDTO;
import com.example.programingappapi.dto.TestCaseDTO;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.TestCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TestCaseMapper {

    public TestCaseDTO toTestCaseDTO(TestCase testCase){
        return TestCaseDTO.builder().
                id(testCase.getId()).
                input(testCase.getInput())
                .expectedOutput(testCase.getExpectedOutput())
                .problemId(testCase.getProblem().getId())
                .build();
    }

    public TestCase createTestCase(TestCaseCreateDTO testCaseCreateDTO, Problem problem)
    {
        return TestCase.builder()
                .expectedOutput(testCaseCreateDTO.getExpectedOutput())
                .problem(problem)
                .input(testCaseCreateDTO.getInput())
                .build();
    }
}
