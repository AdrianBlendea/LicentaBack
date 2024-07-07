package com.example.programingappapi.service;

import com.example.programingappapi.dto.ProblemCreateDTO;
import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.dto.TestCaseCreateDTO;
import com.example.programingappapi.entity.*;
import com.example.programingappapi.exception.ProblemNotFoundException;
import com.example.programingappapi.exception.TypeNotFoundException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.ProblemMapper;
import com.example.programingappapi.mapper.TestCaseMapper;
import com.example.programingappapi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProblemServiceTest {

    @InjectMocks
    private ProblemService problemService;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private ProblemMapper problemMapper;

    @Mock
    private SolutionRepository solutionRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TestCaseMapper testCaseMapper;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private PlagiarismService plagiarismService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProblemById() {
        Long problemId = 1L;
        Long userId = 1L;
        Problem problem = new Problem();
        problem.setId(problemId);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        List<Solution> solutions = new ArrayList<>();
        ProblemDTO problemDTO = new ProblemDTO();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(solutionRepository.findAllByUserAndProblem(userAccount, problem)).thenReturn(solutions);
        when(problemMapper.toProblemDTO(problem, false)).thenReturn(problemDTO);

        ProblemDTO result = problemService.getProblemById(problemId.toString(), userId);

        assertEquals(problemDTO, result);
    }

    @Test
    void testGetProblemById_ProblemNotFound() {
        Long problemId = 1L;
        Long userId = 1L;

        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProblemNotFoundException.class, () -> {
            problemService.getProblemById(problemId.toString(), userId);
        });

        assertEquals("Problem with given id not found", exception.getMessage());
    }

    @Test
    void testGetProblemById_UserNotFound() {
        Long problemId = 1L;
        Long userId = 1L;
        Problem problem = new Problem();
        problem.setId(problemId);

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            problemService.getProblemById(problemId.toString(), userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAllByType() {
        Long typeId = 1L;
        Long userId = 1L;
        Problem problem1 = new Problem();
        problem1.setId(1L);
        Problem problem2 = new Problem();
        problem2.setId(2L);
        List<Problem> problems = Stream.of(problem1, problem2).collect(Collectors.toList());
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        List<Solution> solutions = new ArrayList<>();
        ProblemDTO problemDTO1 = new ProblemDTO();
        ProblemDTO problemDTO2 = new ProblemDTO();

        when(problemRepository.findAllByTypeId(typeId)).thenReturn(problems);
        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(solutionRepository.findAllByUserAndProblem(userAccount, problem1)).thenReturn(solutions);
        when(solutionRepository.findAllByUserAndProblem(userAccount, problem2)).thenReturn(solutions);
        when(problemMapper.toProblemDTO(problem1, false)).thenReturn(problemDTO1);
        when(problemMapper.toProblemDTO(problem2, false)).thenReturn(problemDTO2);

        List<ProblemDTO> result = problemService.getAllByType(typeId, userId);

        assertEquals(2, result.size());
        assertEquals(problemDTO1, result.get(0));
        assertEquals(problemDTO2, result.get(1));
    }

    @Test
    void testGetAllByType_UserNotFound() {
        Long typeId = 1L;
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            problemService.getAllByType(typeId, userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteProblem() {
        Long problemId = 1L;

        String result = problemService.deleteProblem(problemId);

        verify(problemRepository, times(1)).deleteById(problemId);
        assertEquals("Deleted problem succesfully", result);
    }

    @Test
    void testCreateProblem() {
        ProblemCreateDTO problemCreateDTO = new ProblemCreateDTO();
        problemCreateDTO.setName("Test Problem");
        problemCreateDTO.setTypeId(1L);
        problemCreateDTO.setProcentToPass(80L);
        problemCreateDTO.setRequirement("Requirement");
        TestCaseCreateDTO testCaseCreateDTO = new TestCaseCreateDTO();
        List<TestCaseCreateDTO> testList = new ArrayList<>();
        testList.add(testCaseCreateDTO);
        problemCreateDTO.setTestList(testList);

        Type type = new Type();
        type.setId(1L);

        Problem problem = new Problem();
        problem.setId(1L);
        problem.setType(type);
        problem.setName("Test Problem");

        when(typeRepository.findById(problemCreateDTO.getTypeId())).thenReturn(Optional.of(type));
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);
        when(testCaseMapper.createTestCase(any(TestCaseCreateDTO.class), any(Problem.class))).thenReturn(new TestCase());

        String result = problemService.createProblem(problemCreateDTO);

        verify(problemRepository, times(1)).save(any(Problem.class));
        verify(testCaseRepository, times(1)).save(any(TestCase.class));
        verify(plagiarismService, times(1)).createNewFolderForProblem(any(Problem.class));

        assertEquals("Problem saved succesfully", result);
    }

    @Test
    void testCreateProblem_TypeNotFound() {
        ProblemCreateDTO problemCreateDTO = new ProblemCreateDTO();
        problemCreateDTO.setTypeId(1L);

        when(typeRepository.findById(problemCreateDTO.getTypeId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TypeNotFoundException.class, () -> {
            problemService.createProblem(problemCreateDTO);
        });

        assertEquals("Type not found", exception.getMessage());
    }
}
