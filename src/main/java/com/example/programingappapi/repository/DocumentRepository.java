package com.example.programingappapi.repository;


import com.example.programingappapi.entity.Category;
import com.example.programingappapi.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findDocumentsByCategory(Category category);

    List<Document> findAllByCategoryId(Long id);
}