package com.example.programingappapi.service;


import com.example.programingappapi.dto.DocumentDTO;
import com.example.programingappapi.entity.Category;
import com.example.programingappapi.entity.Document;
import com.example.programingappapi.exception.CategoryNotFoundException;
import com.example.programingappapi.mapper.DocumentMapper;
import com.example.programingappapi.repository.CategoryRepository;
import com.example.programingappapi.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void uploadDocument_success() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});

        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));

        Document document = new Document();
        document.setId(1L);
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Long result = documentService.uploadDocument(file, categoryId);

        assertEquals(1L, result);
        verify(documentRepository, times(1)).save(any(Document.class));
    }




    @Test
    void uploadDocument_categoryNotFound() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            documentService.uploadDocument(file, 1L);
        });
    }

    @Test
    void getDocument_success() {
        Document document = new Document();
        document.setId(1L);
        document.setName("test.txt");
        document.setContent(new byte[]{1, 2, 3});
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        ResponseEntity<byte[]> response = documentService.getDocument(1L);

        assertArrayEquals(new byte[]{1, 2, 3}, response.getBody());
    }

    @Test
    void getDocument_notFound() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            documentService.getDocument(1L);
        });
    }

    @Test
    void getAllDocuments() {
        List<Document> documents = Arrays.asList(new Document(), new Document());
        List<DocumentDTO> documentDTOS = Arrays.asList(new DocumentDTO(), new DocumentDTO());
        when(documentRepository.findAll()).thenReturn(documents);
        when(documentMapper.toDocumentDTOS(documents)).thenReturn(documentDTOS);

        List<DocumentDTO> result = documentService.getAllDocuments();

        assertEquals(2, result.size());
        verify(documentRepository, times(1)).findAll();
    }

    @Test
    void getAllDocumentByType() {
        List<Document> documents = Arrays.asList(new Document(), new Document());
        List<DocumentDTO> documentDTOS = Arrays.asList(new DocumentDTO(), new DocumentDTO());
        when(documentRepository.findAllByCategoryId(anyLong())).thenReturn(documents);
        when(documentMapper.toDocumentDTOS(documents)).thenReturn(documentDTOS);

        List<DocumentDTO> result = documentService.getAllDocumentByType(1L);

        assertEquals(2, result.size());
        verify(documentRepository, times(1)).findAllByCategoryId(anyLong());
    }

    @Test
    void getAllDocumentNamesByType() {
        Document document1 = new Document();
        document1.setName("doc1.txt");
        Document document2 = new Document();
        document2.setName("doc2.txt");

        List<Document> documents = Arrays.asList(document1, document2);
        when(documentRepository.findAllByCategoryId(anyLong())).thenReturn(documents);

        List<String> result = documentService.getAllDocumentNamesByType(1L);

        assertEquals(2, result.size());
        assertTrue(result.contains("doc1.txt"));
        assertTrue(result.contains("doc2.txt"));
    }

    @Test
    void deleteDocument() {
        documentService.deleteDocument(1L);
        verify(documentRepository, times(1)).deleteById(1L);
    }
}
