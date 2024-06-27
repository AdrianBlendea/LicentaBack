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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionService {
    private final UserAccountRepository userAccountRepository;
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;
    private final PlagiarismService plagiarismService;

    public String submitSolution(SolutionDTO solutionDTO) {
        Problem problem = problemRepository.findById(solutionDTO.getProblemId())
                .orElseThrow(()-> new ProblemNotFoundException("Problem can't pe found"));

        UserAccount user = userAccountRepository.findById(solutionDTO.getUserId())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        Solution solution = Solution.builder().user(user).problem(problem).language(solutionDTO.getLanguage())
                .solution(solutionDTO.getSolution()).build();
        solutionRepository.save(solution);
        plagiarismService.createNewFileForSubmission(solution);



        return "Solution submited succesfully";

    }

    public SolutionDTO getSolutionByUser(Long userId, Long problemId)
    {
        UserAccount userAccount = userAccountRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));

        Problem problem =problemRepository.findById(problemId).orElseThrow(() -> new ProblemNotFoundException("Problem not found"));

        List<Solution> solutions = solutionRepository.findAllByUserAndProblem(userAccount, problem);
        if(solutions.isEmpty()){
            throw new SolutionNotFoundException("Solution for the problem by the user not found");
        }

        return  solutionMapper.toSolutionDto(solutions.get(0));
    }
    @Transactional
    public String deleteSolutionByUser(Long userId, Long problemId) {
        UserAccount userAccount = userAccountRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found"));

        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new ProblemNotFoundException("Problem not found"));

        solutionRepository.deleteAllByUserAndProblem(userAccount, problem);

        return "Solution deleted succesfully";
    }

    public Long getSolutionCountForProblem(Long problemId, String language)
    {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new ProblemNotFoundException("Problem not found"));

        return problem.getSolutionList().stream().filter(sol-> sol.getLanguage().equals(language)).count();
    }
}
