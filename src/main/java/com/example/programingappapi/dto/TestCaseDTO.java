package com.example.programingappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TestCaseDTO {

    private Long id;

    private String input;

    private String expectedOutput;

    private Long problemId;
}
