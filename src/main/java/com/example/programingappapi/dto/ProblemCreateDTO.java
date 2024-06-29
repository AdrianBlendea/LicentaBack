package com.example.programingappapi.dto;

import com.example.programingappapi.entity.Type;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProblemCreateDTO {
    @NotNull
    private String name;

    @NotNull
    private String requirement;
    @NotNull
    Long typeId;
    @NotNull
    private Long procentToPass;

    @NotNull
    List<TestCaseCreateDTO> testList;
}
