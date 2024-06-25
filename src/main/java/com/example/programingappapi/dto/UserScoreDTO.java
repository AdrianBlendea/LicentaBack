package com.example.programingappapi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScoreDTO {

    private String name;

    private Long score;

    private String email;

    private byte[] profilePicture;
}
