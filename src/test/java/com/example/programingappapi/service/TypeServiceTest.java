package com.example.programingappapi.service;

import com.example.programingappapi.dto.TypeDTO;
import com.example.programingappapi.entity.Type;
import com.example.programingappapi.mapper.TypeMapper;
import com.example.programingappapi.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TypeServiceTest {

    @InjectMocks
    private TypeService typeService;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeMapper typeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTypes() {
        List<TypeDTO> typeDTOList = Stream.of(new TypeDTO(1L, "Type1"), new TypeDTO(2L, "Type2")).collect(Collectors.toList());
        Type type1 = new Type();
        type1.setId(1L);
        type1.setName("Type1");
        Type type2 = new Type();
        type2.setId(2L);
        type2.setName("Type2");
        when(typeRepository.findAll()).thenReturn(Stream.of(type1, type2).collect(Collectors.toList()));
        when(typeMapper.toTypeDTO(any(Type.class))).thenReturn(typeDTOList.get(0), typeDTOList.get(1));

        List<TypeDTO> result = typeService.getAllTypes();

        assertEquals(2, result.size());
        assertEquals("Type1", result.get(0).getName());
        assertEquals("Type2", result.get(1).getName());
    }
}
