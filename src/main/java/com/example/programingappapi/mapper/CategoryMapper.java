package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.CategoryDTO;
import com.example.programingappapi.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CategoryMapper {

    public CategoryDTO categoryDTO(Category category)
    {
        return CategoryDTO.builder().id(category.getId()).name(category.getName()).build();
    }
}
