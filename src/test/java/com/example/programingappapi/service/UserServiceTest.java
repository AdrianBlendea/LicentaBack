package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserTokenDTO;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.AccountNotEnabledException;
import com.example.programingappapi.exception.PasswordAndEmailNotMatchingException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.jwtutil.JwtTokenProvider;
import com.example.programingappapi.mapper.UserTokenMapper;
import com.example.programingappapi.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private UserTokenMapper userTokenMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        String email = "test@example.com";
        String password = "password";
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword("encodedPassword");
        userAccount.setEnabled(true);
        userAccount.setRole("user");

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(passwordEncoder.matches(password, userAccount.getPassword())).thenReturn(true);
        when(userTokenMapper.toUserTokenDTO(userAccount, JwtTokenProvider.generateToken(email, List.of(userAccount.getRole()))))
                .thenReturn(new UserTokenDTO());

        ResponseEntity<UserTokenDTO> response = userService.login(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogin_UserNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.login(email, password);
        });

        assertEquals("Username not found", exception.getMessage());
    }

    @Test
    void testLogin_PasswordMismatch() {
        String email = "test@example.com";
        String password = "password";
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword("encodedPassword");
        userAccount.setEnabled(true);

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(passwordEncoder.matches(password, userAccount.getPassword())).thenReturn(false);

        Exception exception = assertThrows(PasswordAndEmailNotMatchingException.class, () -> {
            userService.login(email, password);
        });

        assertEquals("Password and username dont match", exception.getMessage());
    }

    @Test
    void testLogin_AccountNotEnabled() {
        String email = "test@example.com";
        String password = "password";
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword("encodedPassword");
        userAccount.setEnabled(false);

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(passwordEncoder.matches(password, userAccount.getPassword())).thenReturn(true);

        Exception exception = assertThrows(AccountNotEnabledException.class, () -> {
            userService.login(email, password);
        });

        assertEquals("Your account is not enabled yet please check the confirmation email we have send", exception.getMessage());
    }

    @Test
    void testSetProfilePicture() throws IOException {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        byte[] pictureBytes = new byte[]{1, 2, 3};

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(multipartFile.getBytes()).thenReturn(pictureBytes);

        String result = userService.setProfilePicture(multipartFile, userId);

        assertEquals("Profile picture saved sucessfully", result);
        assertArrayEquals(pictureBytes, userAccount.getProfilePicture());
    }

    @Test
    void testSetProfilePicture_UserNotFound() throws IOException {
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.setProfilePicture(multipartFile, userId);
        });

        assertEquals("user not found", exception.getMessage());
    }

    @Test
    void testGetProfilePicture() throws IOException {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        byte[] pictureBytes = new byte[]{1, 2, 3};
        userAccount.setProfilePicture(pictureBytes);

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));

        byte[] result = userService.getProfilePicture(userId);

        assertArrayEquals(pictureBytes, result);
    }

    @Test
    void testGetProfilePicture_UserNotFound() throws IOException {
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getProfilePicture(userId);
        });

        assertEquals("user not found", exception.getMessage());
    }

    @Test
    void testDeleteProfilePicture() throws IOException {
        Long userId = 1L;
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        userAccount.setProfilePicture(new byte[]{1, 2, 3});

        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));

        String result = userService.deleteProfilePicture(userId);

        assertEquals("Profile picture deleted sucessfully", result);
        assertNull(userAccount.getProfilePicture());
    }

    @Test
    void testDeleteProfilePicture_UserNotFound() throws IOException {
        Long userId = 1L;

        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteProfilePicture(userId);
        });

        assertEquals("user not found", exception.getMessage());
    }
}
