package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.TypeDTO;
import com.example.programingappapi.entity.Type;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TypeMapper {

    public TypeDTO toTypeDTO(Type t)
    {
        return TypeDTO.builder().id(t.getId()).name(t.getName()).build();
    }
}
