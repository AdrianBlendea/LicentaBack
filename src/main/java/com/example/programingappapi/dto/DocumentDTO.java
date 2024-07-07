package com.example.programingappapi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    private Long id;

    private String name;

    private byte[] content;

    private String category;
}
