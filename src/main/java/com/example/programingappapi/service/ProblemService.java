package com.example.programingappapi.service;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.Solution;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.ProblemNotFoundException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.ProblemMapper;
import com.example.programingappapi.repository.ProblemRepository;
import com.example.programingappapi.repository.SolutionRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final ProblemMapper problemMapper;

    private final SolutionRepository solutionRepository;

    private final UserAccountRepository userAccountRepository;


    public ProblemDTO getProblemById(String id, Long userId) {
        Problem problem = problemRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given id not found"));

        UserAccount userAccount = userAccountRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Solution> solutions = solutionRepository.findAllByUserAndProblem(userAccount, problem);

        ProblemDTO problemDTO = solutions.isEmpty() ? problemMapper.toProblemDTO(problem, false)
                : problemMapper.toProblemDTO(problem, true);

        return problemDTO;

    }

    public List<ProblemDTO> getAllByType(Long typeId, Long userId) {
        List<Problem> problems = problemRepository.findAllByTypeId(typeId);
        UserAccount userAccount = userAccountRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));

        List<ProblemDTO> problemDTOS = new ArrayList<>();

        for (Problem p : problems) {
            List<Solution> solutions = solutionRepository.findAllByUserAndProblem(userAccount, p);
            ProblemDTO problemDTO = solutions.isEmpty() ? problemMapper.toProblemDTO(p, false)
                    : problemMapper.toProblemDTO(p, true);
            problemDTOS.add(problemDTO);
        }
        return problemDTOS;
    }
}
