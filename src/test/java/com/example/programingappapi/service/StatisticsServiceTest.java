package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserScoreDTO;
import com.example.programingappapi.entity.Solution;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.mapper.UserMapper;
import com.example.programingappapi.repository.SolutionRepository;
import com.example.programingappapi.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private SolutionRepository solutionRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNumberOfProblems_UserExists() {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);

        List<Solution> solutions = new ArrayList<>();
        solutions.add(new Solution());
        solutions.add(new Solution());

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(solutionRepository.findAllByUser(userAccount)).thenReturn(solutions);

        int result = statisticsService.getNumberOfProblems(userId);

        assertEquals(2, result);
    }

    @Test
    void testGetNumberOfProblems_UserNotFound() {
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            statisticsService.getNumberOfProblems(userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetLeaderboardPlace() {
        Long userId = 1L;
        UserAccount userAccount1 = new UserAccount();
        userAccount1.setId(userId);
        userAccount1.setEmail("user1@example.com");

        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(2L);
        userAccount2.setEmail("user2@example.com");

        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        userAccounts.add(userAccount1);
        userAccounts.add(userAccount2);

        List<Solution> solutions1 = List.of(
                createSolution(80L),
                createSolution(70L)
        );

        List<Solution> solutions2 = List.of(
                createSolution(60L),
                createSolution(50L)
        );
    ;
        when(userAccountRepository.findAll()).thenReturn(userAccounts);
        when(solutionRepository.findAllByUser(userAccount1)).thenReturn(solutions1);
        when(solutionRepository.findAllByUser(userAccount2)).thenReturn(solutions2);

        String result = statisticsService.getLeaderboardPlace(userId);

        assertEquals("1 din 2", result);
    }

    @Test
    void testGetLeaderBoard() {
        UserAccount userAccount1 = new UserAccount();
        userAccount1.setId(1L);
        userAccount1.setEmail("user1@example.com");

        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(2L);
        userAccount2.setEmail("user2@example.com");

        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        userAccounts.add(userAccount1);
        userAccounts.add(userAccount2);

        List<Solution> solutions1 = List.of(
                createSolution(80L),
                createSolution(70L)
        );

        List<Solution> solutions2 = List.of(
                createSolution(60L),
                createSolution(50L)
        );

        when(userAccountRepository.findAll()).thenReturn(userAccounts);
        when(solutionRepository.findAllByUser(userAccount1)).thenReturn(solutions1);
        when(solutionRepository.findAllByUser(userAccount2)).thenReturn(solutions2);

        UserScoreDTO userScoreDTO1 = new UserScoreDTO();
        userScoreDTO1.setName("User1");
        userScoreDTO1.setScore(150L);
        userScoreDTO1.setEmail("user1@example.com");
        userScoreDTO1.setProfilePicture(null);

        UserScoreDTO userScoreDTO2 = new UserScoreDTO();
        userScoreDTO2.setName("User2");
        userScoreDTO2.setScore(110L);
        userScoreDTO2.setEmail("user2@example.com");
        userScoreDTO2.setProfilePicture(null);

        when(userMapper.userScoreDTO(userAccount1, 150L)).thenReturn(userScoreDTO1);
        when(userMapper.userScoreDTO(userAccount2, 110L)).thenReturn(userScoreDTO2);

        List<UserScoreDTO> result = statisticsService.getLeaderBoard();

        assertEquals(2, result.size());
        assertEquals(150L, result.get(0).getScore());
        assertEquals(110L, result.get(1).getScore());
    }

    private Solution createSolution(Long procentScored) {
        Solution solution = new Solution();
        solution.setProcentScored(procentScored);
        return solution;
    }
}
