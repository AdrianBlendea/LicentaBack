package com.example.programingappapi.service;

import com.example.programingappapi.dto.CategoryDTO;
import com.example.programingappapi.entity.Category;
import com.example.programingappapi.mapper.CategoryMapper;
import com.example.programingappapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Create Category entities
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");

        // Create CategoryDTO objects
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("Category1");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Category2");

        // Mock repository and mapper methods
        when(categoryRepository.findAll()).thenReturn(Stream.of(category1, category2).collect(Collectors.toList()));
        when(categoryMapper.categoryDTO(any(Category.class))).thenReturn(categoryDTO1, categoryDTO2);

        // Call the service method
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Verify the results
        assertEquals(2, result.size());
        assertEquals("Category1", result.get(0).getName());
        assertEquals("Category2", result.get(1).getName());
    }
}
