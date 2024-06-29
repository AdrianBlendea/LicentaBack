package com.example.programingappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProblemDTO {
    private Long id;

    private String name;

    private String requirment;

    private boolean solved;

    private Long procentToPass;

}
