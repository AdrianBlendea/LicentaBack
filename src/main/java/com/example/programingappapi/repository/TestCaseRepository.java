package com.example.programingappapi.repository;

import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    public List<TestCase> findAllByProblem_Id(Long problemId);
}
