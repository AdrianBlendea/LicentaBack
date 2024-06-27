package com.example.programingappapi.service;

import com.example.programingappapi.dto.LeaderboardPlaceDTO;
import com.example.programingappapi.dto.UserScoreDTO;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.UserMapper;
import com.example.programingappapi.repository.SolutionRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final UserAccountRepository userAccountRepository;
    private final SolutionRepository solutionRepository;
    private final UserMapper userMapper;

    @Transactional
    public int getNumberOfProblems(Long userId)
    {
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        return solutionRepository.findAllByUser(user).size();
    }


    public String getLeaderboardPlace(Long userId)
    {
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        Map<UserAccount, Long> userSolutionCounts = userAccounts.stream()
                .collect(Collectors.toMap(
                        userAccount -> userAccount,
                        userAccount -> (long) solutionRepository.findAllByUser(userAccount).size()
                ));

        List<UserAccount> sortedUserAccounts = userSolutionCounts.entrySet().stream()
                .sorted(Map.Entry.<UserAccount, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int place = IntStream.range(0, sortedUserAccounts.size())
                .filter(i -> sortedUserAccounts.get(i).getId().equals(userId))
                .findFirst()
                .orElse(-1) + 1;

        return place + " din " + userAccounts.size();
    }

    public List<UserScoreDTO> getLeaderBoard()
    { List<UserAccount> userAccounts = userAccountRepository.findAll();
        Map<UserAccount, Long> userSolutionCounts = userAccounts.stream()
                .collect(Collectors.toMap(
                        userAccount -> userAccount,
                        userAccount -> (long) solutionRepository.findAllByUser(userAccount).size()
                ));
        Stream<Map.Entry<UserAccount, Long>> entryStream = userSolutionCounts.entrySet().stream();


        List<UserScoreDTO> userScoreDTOS = new ArrayList<>();
        entryStream.forEach(us -> userScoreDTOS.add(userMapper.userScoreDTO(us.getKey(), us.getValue())));

        return userScoreDTOS;



    }
}
