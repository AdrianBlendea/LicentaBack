package com.example.programingappapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_case")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String input;

    private String expectedOutput;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
}
