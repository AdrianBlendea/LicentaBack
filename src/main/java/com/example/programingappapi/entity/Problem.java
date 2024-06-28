package com.example.programingappapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "requirment", nullable = false, length = 10000)
    private String requirment;

    @ManyToOne(fetch = FetchType.LAZY)
     private Type type;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TestCase> testCaseList;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Solution> solutionList;

    @Column(name = "procentToPass", nullable = false)
    private Long procentToPass;

}
