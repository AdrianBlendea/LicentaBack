package com.example.programingappapi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDTO {

    String solution;

    Long problemId;

    Long userId;

    String language;
}
