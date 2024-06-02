package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.TestCaseDTO;
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
}
