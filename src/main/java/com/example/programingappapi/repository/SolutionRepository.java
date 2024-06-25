package com.example.programingappapi.repository;

import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.Solution;
import com.example.programingappapi.entity.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {

    List<Solution> findAllByUserAndProblem(UserAccount u, Problem p);

    List<Solution> findAllByUser(UserAccount u);

    void deleteAllByUserAndProblem(UserAccount u, Problem p);
}
