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

    public ProblemDTO toProblemDTO(Problem problem, boolean solved) {
        return ProblemDTO.builder()
                .id(problem.getId())
                .requirment(problem.getRequirment())
                .name(problem.getName())
                .solved(solved)
                .build();


    }




}
