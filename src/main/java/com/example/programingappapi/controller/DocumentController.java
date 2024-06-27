package com.example.programingappapi.controller;

import com.example.programingappapi.dto.DocumentDTO;
import com.example.programingappapi.service.DocumentService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @GetMapping("/download-zip")
    public ResponseEntity<FileSystemResource> downloadZip() {
        // Define the path to the existing zip file
        String zipFilePath = "C:/licentaback/plagiarism/rez/archive.zip";
        File zipFile = new File(zipFilePath);

        if (!zipFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Create a FileSystemResource from the file
        FileSystemResource resource = new FileSystemResource(zipFile);

        // Set the headers and content type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        // Return the file as a resource in the response entity
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .body(resource);
    }

}
