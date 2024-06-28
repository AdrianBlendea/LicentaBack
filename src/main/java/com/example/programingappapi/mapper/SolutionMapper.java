package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.SolutionDTO;
import com.example.programingappapi.entity.Solution;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SolutionMapper {

    public SolutionDTO toSolutionDto(Solution s)
    {
        return SolutionDTO.
                builder()
                .solution(s.getSolution())
                .language(s.getLanguage())
                .problemId(s.getProblem().getId())
                .procentScored(s.getProcentScored())
                .userId(s.getUser().getId()).build();
    }

}
