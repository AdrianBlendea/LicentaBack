package com.example.programingappapi.controller;

import com.example.programingappapi.dto.DocumentDTO;
import com.example.programingappapi.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public Long uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("category") Long categoryId) {
        return documentService.uploadDocument(file, categoryId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocument(@PathVariable Long id) {
        return documentService.getDocument(id);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments()
    {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<DocumentDTO>> getAllByType(@PathVariable Long id)
    {
        return ResponseEntity.ok(documentService.getAllDocumentByType(id));
    }

    @GetMapping("/all/noauth/{id}")
    public ResponseEntity<List<String>> getAllNamesByType(@PathVariable Long id)
    {
        return ResponseEntity.ok(documentService.getAllDocumentNamesByType(id));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocument(@RequestParam("documentId")Long documentId)
    {
        return ResponseEntity.ok(documentService.deleteDocument(documentId));
    }

}
