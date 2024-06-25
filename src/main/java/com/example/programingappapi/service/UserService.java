package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.dto.UserDTO;
import com.example.programingappapi.dto.UserTokenDTO;
import com.example.programingappapi.dto.UserUpdateDTO;
import com.example.programingappapi.entity.UserAccount;
import com.example.programingappapi.exception.AccountNotEnabledException;
import com.example.programingappapi.exception.PasswordAndEmailNotMatchingException;
import com.example.programingappapi.exception.UserNotAdminException;
import com.example.programingappapi.exception.UserNotFoundException;
import com.example.programingappapi.jwtutil.JwtTokenProvider;
import com.example.programingappapi.mapper.UserMapper;
import com.example.programingappapi.mapper.UserTokenMapper;
import com.example.programingappapi.repository.UserAccountRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static String deviceBackendUrl = "http://localhost:8081/api/user";
    private final UserAccountRepository userAccountRepository;
    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;

    @Transactional
    public ResponseEntity<UserTokenDTO> login(String email, String password) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!userAccount.getPassword().equals(password)) {
            throw new PasswordAndEmailNotMatchingException("Password and username dont match");
        }
        if (!userAccount.isEnabled()) {
            throw new AccountNotEnabledException("Your account is not enabled yet please check the confirmation email we have send");
        }
        return new ResponseEntity<>(userTokenMapper.toUserTokenDTO(userAccount, JwtTokenProvider.generateToken(email)), HttpStatus.OK);

    }
    @Transactional
    public String setProfilePicture(MultipartFile picture, Long userId) throws IOException {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        userAccount.setProfilePicture(picture.getBytes());

        return "Profile picture saved sucessfully";


    }

    @Transactional
    public byte[] getProfilePicture( Long userId) throws IOException {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("user not found"));


        return userAccount.getProfilePicture();


    }

    @Transactional
    public String deleteProfilePicture(Long userId) throws IOException {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        userAccount.setProfilePicture(null);

        return "Profile picture deleted sucessfully";


    }



}
