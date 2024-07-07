package com.example.programingappapi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO {
    private Long id;

    private String name;

    private String requirment;

    private boolean solved;

    private Long procentToPass;

}
