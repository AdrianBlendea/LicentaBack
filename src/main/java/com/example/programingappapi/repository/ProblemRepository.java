package com.example.programingappapi.repository;

import com.example.programingappapi.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>
{
    List<Problem> findAll();
}
