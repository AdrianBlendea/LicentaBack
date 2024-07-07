package com.example.programingappapi.service;

import com.example.programingappapi.dto.SolutionDTO;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.Solution;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.ProblemNotFoundException;
import com.example.programingappapi.exception.SolutionNotFoundException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.SolutionMapper;
import com.example.programingappapi.repository.ProblemRepository;
import com.example.programingappapi.repository.SolutionRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SolutionServiceTest {

    @InjectMocks
    private SolutionService solutionService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private SolutionRepository solutionRepository;

    @Mock
    private SolutionMapper solutionMapper;

    @Mock
    private PlagiarismService plagiarismService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitSolution() {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemId(1L);
        solutionDTO.setUserId(1L);
        solutionDTO.setLanguage("java");
        solutionDTO.setSolution("public class Solution {}");
        solutionDTO.setProcentScored(100L);

        Problem problem = new Problem();
        UserAccount user = new UserAccount();

        when(problemRepository.findById(solutionDTO.getProblemId())).thenReturn(Optional.of(problem));
        when(userAccountRepository.findById(solutionDTO.getUserId())).thenReturn(Optional.of(user));
        when(solutionRepository.save(any(Solution.class))).thenReturn(new Solution());

        String result = solutionService.submitSolution(solutionDTO);

        verify(solutionRepository, times(1)).save(any(Solution.class));
        verify(plagiarismService, times(1)).createNewFileForSubmission(any(Solution.class));

        assertEquals("Solution submited succesfully", result);
    }

    @Test
    void testSubmitSolution_ProblemNotFound() {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemId(1L);
        solutionDTO.setUserId(1L);

        when(problemRepository.findById(solutionDTO.getProblemId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProblemNotFoundException.class, () -> {
            solutionService.submitSolution(solutionDTO);
        });

        assertEquals("Problem can't pe found", exception.getMessage());
    }

    @Test
    void testSubmitSolution_UserNotFound() {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemId(1L);
        solutionDTO.setUserId(1L);
        Problem problem = new Problem();

        when(problemRepository.findById(solutionDTO.getProblemId())).thenReturn(Optional.of(problem));
        when(userAccountRepository.findById(solutionDTO.getUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            solutionService.submitSolution(solutionDTO);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetSolutionByUser() {
        Long userId = 1L;
        Long problemId = 1L;
        UserAccount userAccount = new UserAccount();
        Problem problem = new Problem();
        Solution solution = new Solution();
        List<Solution> solutions = List.of(solution);
        SolutionDTO solutionDTO = new SolutionDTO();

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(solutionRepository.findAllByUserAndProblem(userAccount, problem)).thenReturn(solutions);
        when(solutionMapper.toSolutionDto(any(Solution.class))).thenReturn(solutionDTO);

        SolutionDTO result = solutionService.getSolutionByUser(userId, problemId);

        assertEquals(solutionDTO, result);
    }

    @Test
    void testGetSolutionByUser_UserNotFound() {
        Long userId = 1L;
        Long problemId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            solutionService.getSolutionByUser(userId, problemId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetSolutionByUser_ProblemNotFound() {
        Long userId = 1L;
        Long problemId = 1L;
        UserAccount userAccount = new UserAccount();

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProblemNotFoundException.class, () -> {
            solutionService.getSolutionByUser(userId, problemId);
        });

        assertEquals("Problem not found", exception.getMessage());
    }

    @Test
    void testGetSolutionByUser_SolutionNotFound() {
        Long userId = 1L;
        Long problemId = 1L;
        UserAccount userAccount = new UserAccount();
        Problem problem = new Problem();

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(solutionRepository.findAllByUserAndProblem(userAccount, problem)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(SolutionNotFoundException.class, () -> {
            solutionService.getSolutionByUser(userId, problemId);
        });

        assertEquals("Solution for the problem by the user not found", exception.getMessage());
    }

    @Test
    void testDeleteSolutionByUser() {
        Long userId = 1L;
        Long problemId = 1L;
        UserAccount userAccount = new UserAccount();
        Problem problem = new Problem();

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        String result = solutionService.deleteSolutionByUser(userId, problemId);

        verify(solutionRepository, times(1)).deleteAllByUserAndProblem(userAccount, problem);
        assertEquals("Solution deleted succesfully", result);
    }

    @Test
    void testDeleteSolutionByUser_UserNotFound() {
        Long userId = 1L;
        Long problemId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            solutionService.deleteSolutionByUser(userId, problemId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteSolutionByUser_ProblemNotFound() {
        Long userId = 1L;
        Long problemId = 1L;
        UserAccount userAccount = new UserAccount();

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProblemNotFoundException.class, () -> {
            solutionService.deleteSolutionByUser(userId, problemId);
        });

        assertEquals("Problem not found", exception.getMessage());
    }


    @Test
    void testGetSolutionCountForProblem_ProblemNotFound() {
        Long problemId = 1L;
        String language = "java";

        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProblemNotFoundException.class, () -> {
            solutionService.getSolutionCountForProblem(problemId, language);
        });

        assertEquals("Problem not found", exception.getMessage());
    }
}
