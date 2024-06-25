package com.example.programingappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LeaderboardPlaceDTO {

    private int place;

    private int totalUsers;
}
