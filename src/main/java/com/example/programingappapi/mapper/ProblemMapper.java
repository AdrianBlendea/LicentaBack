package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.ProblemDTO;
import com.example.programingappapi.entity.Problem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ProblemMapper {

    ProblemDTO toProblemDTO(Problem problem) {
        return ProblemDTO.builder()
                .id(problem.getId())
                .requirment(problem.getRequirment())
                .name(problem.getName()).build();


    }

    public List<ProblemDTO> toProblemDtos(List<Problem> problems)
    {   List<ProblemDTO> problemDTOS = new ArrayList<>();
        problems.stream().forEach(p-> problemDTOS.add(toProblemDTO(p)));
        return  problemDTOS;
    }


}
