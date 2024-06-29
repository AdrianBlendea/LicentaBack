package com.example.programingappapi.service;

import com.example.programingappapi.dto.ProblemCreateDTO;
import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.entity.*;
import com.example.programingappapi.exception.ProblemNotFoundException;
import com.example.programingappapi.exception.TypeNotFoundException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.ProblemMapper;
import com.example.programingappapi.mapper.TestCaseMapper;
import com.example.programingappapi.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final ProblemMapper problemMapper;

    private final SolutionRepository solutionRepository;

    private final UserAccountRepository userAccountRepository;

    private final TypeRepository typeRepository;

    private final TestCaseMapper testCaseMapper;

    private final TestCaseRepository testCaseRepository;

    private final PlagiarismService plagiarismService;

    @Transactional
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
    @Transactional
    public List<ProblemDTO> getAllByType(Long typeId, Long userId) {
        if(userId == null){
            return problemRepository.findAllByTypeId(typeId).stream().map(p->problemMapper.toProblemDTO(p, false)).collect(Collectors.toList());
        }
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

    @Transactional
    public String deleteProblem(Long problemId)
    {
        problemRepository.deleteById(problemId);
        return "Deleted problem succesfully";
    }

    @Transactional
    public String createProblem(ProblemCreateDTO problemCreateDTO)
    {
        Type type = typeRepository.findById(problemCreateDTO.getTypeId())
                .orElseThrow(()-> new TypeNotFoundException("Type not found"));

       Problem problemToSave = Problem.builder()
               .name(problemCreateDTO.getName())
               .type(type)
               .procentToPass(problemCreateDTO.getProcentToPass())
               .requirment(problemCreateDTO.getRequirement())
               .build();

       Problem savedProblem = problemRepository.save(problemToSave);

       problemCreateDTO.getTestList().forEach(ts ->{
           TestCase testcaseToSave = testCaseMapper.createTestCase(ts, savedProblem);
           testCaseRepository.save(testcaseToSave);

       });

       plagiarismService.createNewFolderForProblem(savedProblem);
        return "Problem saved succesfully";

    }

}
