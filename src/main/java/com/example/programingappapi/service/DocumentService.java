package com.example.programingappapi.service;

import com.example.programingappapi.dto.DocumentDTO;
import com.example.programingappapi.entity.Document;
import com.example.programingappapi.exception.CategoryNotFoundException;
import com.example.programingappapi.mapper.DocumentMapper;
import com.example.programingappapi.repository.CategoryRepository;
import com.example.programingappapi.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long uploadDocument(MultipartFile file, Long categoryId) {
        try {
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setContent(file.getBytes());
            document.setCategory(categoryRepository.findById(categoryId).
                    orElseThrow(()->new CategoryNotFoundException("No category with this id found")));
            return documentRepository.save(document).getId();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload document", e);
        }
    }
    @Transactional
    public ResponseEntity<byte[]> getDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"")
                .body(document.getContent());
    }
    @Transactional
    public List<DocumentDTO> getAllDocuments() {
        return documentMapper.toDocumentDTOS(documentRepository.findAll());
    }

    @Transactional
    public List<DocumentDTO> getAllDocumentByType(Long categoryId) {
        List<DocumentDTO> documentDTOS = documentMapper.toDocumentDTOS(documentRepository.findAllByCategoryId(categoryId));
        documentDTOS.forEach(d-> d.setContent(null));
        return  documentDTOS;
    }
    @Transactional
    public List<String> getAllDocumentNamesByType(Long categoryId) {
        List<String> documentNames = new ArrayList<>();
        documentRepository.findAllByCategoryId(categoryId).stream().forEach(t-> documentNames.add(t.getName()));
        return documentNames;
    }

    @Transactional
    public String deleteDocument(Long documentId)
    {
        documentRepository.deleteById(documentId);
        return "document deleted succesfully";
    }


}
