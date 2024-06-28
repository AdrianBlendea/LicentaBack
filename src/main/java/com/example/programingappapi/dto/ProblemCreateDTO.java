package com.example.programingappapi.dto;

import com.example.programingappapi.entity.Type;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProblemCreateDTO {

    private String name;


    private String requirement;

    Long typeId;

    private Long procentToPass;


    List<TestCaseCreateDTO> testList;
}
