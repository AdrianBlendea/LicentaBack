package com.example.programingappapi.service;

import com.example.programingappapi.dto.TestCaseDTO;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.TestCase;
import com.example.programingappapi.mapper.TestCaseMapper;
import com.example.programingappapi.repository.TestCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestCaseServiceTest {

    @InjectMocks
    private TestCaseService testCaseService;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private TestCaseMapper testCaseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTestsForProblem() {
        Long problemId = 1L;
        List<TestCaseDTO> testCaseDTOList = Stream.of(new TestCaseDTO(1L, "Input1", "Output1", null), new TestCaseDTO(2L, "Input2", "Output2", null)).collect(Collectors.toList());
        when(testCaseRepository.findAllByProblem_Id(problemId)).thenReturn(Stream.of(new TestCase(1L, "Input1", "Output1", new Problem()), new TestCase(2L, "Input2", "Output2", new Problem())).collect(Collectors.toList()));
        when(testCaseMapper.toTestCaseDTO(any(TestCase.class))).thenReturn(testCaseDTOList.get(0), testCaseDTOList.get(1));

        List<TestCaseDTO> result = testCaseService.getAllTestsForProblem(problemId);

        assertEquals(2, result.size());
        assertEquals("Input1", result.get(0).getInput());
        assertEquals("Input2", result.get(1).getInput());
    }
}
