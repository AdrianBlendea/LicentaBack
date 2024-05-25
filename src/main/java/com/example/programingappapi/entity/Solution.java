package com.example.programingappapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="solution")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "solution",nullable = false, length = 10000)
    private String solution;


}
