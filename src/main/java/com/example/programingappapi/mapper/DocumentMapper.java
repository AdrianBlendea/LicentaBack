package com.example.programingappapi.mapper;

import com.example.programingappapi.dto.DocumentDTO;
import com.example.programingappapi.entity.Document;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class DocumentMapper {

    public DocumentDTO toDocumentDTO(Document document) {
        return DocumentDTO.builder().id(document.getId()).content(document.getContent()).
                name(document.getName()).category(document.getCategory().getName()).build();
    }

    public List<DocumentDTO> toDocumentDTOS(List<Document> documents) {
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        documents.stream().forEach(d -> documentDTOS.add(toDocumentDTO(d)));
        return documentDTOS;
    }
}
