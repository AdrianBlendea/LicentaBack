package com.example.programingappapi.service;

import com.example.programingappapi.dto.TypeDTO;
import com.example.programingappapi.mapper.TypeMapper;
import com.example.programingappapi.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    public List<TypeDTO> getAllTypes()
    {
        return typeRepository.findAll().stream()
                .map(typeMapper::toTypeDTO) // Map each Type to TypeDTO
                .collect(Collectors.toList());
    }

}
