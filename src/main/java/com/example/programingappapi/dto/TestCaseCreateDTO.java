package com.example.programingappapi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseCreateDTO {
    private Long id;

    private String input;

    private String expectedOutput;
}
