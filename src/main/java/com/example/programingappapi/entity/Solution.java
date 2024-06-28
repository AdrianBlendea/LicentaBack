package com.example.programingappapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="solution")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "solution",nullable = false, length = 10000)
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @ManyToOne
    private UserAccount user;

    @Column(name = "language", nullable = false)
    private String language;



}
