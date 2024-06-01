package com.example.programingappapi.service;

import com.example.programingappapi.dto.CategoryDTO;
import com.example.programingappapi.mapper.CategoryMapper;
import com.example.programingappapi.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategories()
    {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryDTO).collect(Collectors.toList());
    }
}
