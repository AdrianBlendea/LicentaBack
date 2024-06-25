package com.example.programingappapi.controller;

import com.example.programingappapi.dto.LeaderboardPlaceDTO;
import com.example.programingappapi.dto.UserScoreDTO;
import com.example.programingappapi.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/problems")
    public ResponseEntity<Integer> getNumberOfProblems(@RequestParam("userId")Long userId)
    {
        return ResponseEntity.ok(statisticsService.getNumberOfProblems(userId));
    }

    @GetMapping("/lplace")
    public ResponseEntity<String> getLeaderboardPlace(@RequestParam("userId")Long userId)
    {
        return ResponseEntity.ok(statisticsService.getLeaderboardPlace(userId));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserScoreDTO>> getLeaderBoard()
    {
        return ResponseEntity.ok(statisticsService.getLeaderBoard());
    }

}
