package com.example.programingappapi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenDTO {
    private String name;

    private String role;

    private String email;

    private Long id;

    private String token;
}
