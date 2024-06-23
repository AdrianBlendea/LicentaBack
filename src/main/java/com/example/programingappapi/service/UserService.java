package com.example.programingappapi.service;

import com.example.programingappapi.dto.UserCreateDTO;
import com.example.programingappapi.dto.UserDTO;
import com.example.programingappapi.dto.UserTokenDTO;
import com.example.programingappapi.dto.UserUpdateDTO;
import com.example.programingappapi.entity.UserAccount;
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

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserAccountRepository userAccountRepository;
  private final UserMapper userMapper;
  private final UserTokenMapper userTokenMapper;
  private final static String deviceBackendUrl = "http://localhost:8081/api/user";

  @Transactional
  public ResponseEntity<UserTokenDTO> login(String email, String password) {
    UserAccount userAccount = userAccountRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    if (!userAccount.getPassword().equals(password)) {
      throw new PasswordAndEmailNotMatchingException("Password and username dont match");
    }

    return new ResponseEntity<>(userTokenMapper.toUserTokenDTO(userAccount, JwtTokenProvider.generateToken(email)), HttpStatus.OK);

  }

  public ResponseEntity<String> createUser(@NonNull UserCreateDTO userCreateDTO) {

    UserAccount userAccount = UserAccount.builder().name(userCreateDTO.getName())
        .email(userCreateDTO.getEmail()).password(userCreateDTO.getPassword())
        .role(userCreateDTO.getRole()).build();


    Long userId = userAccountRepository.save(userAccount).getId();
    System.out.println(userId);

    return new ResponseEntity<>("User created succesfully", HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<String> deleteUser(@NonNull Long id) {
    userAccountRepository.deleteUserAccountById(id);
    try {
      URL url = new URL(deviceBackendUrl + '/' + id);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set the request method to POST
      connection.setRequestMethod("DELETE");

      // Get the response code
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>("User deleted succesfully", HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<String> updateUser(@NonNull UserUpdateDTO userUpdateDTO, Long id) {
    UserAccount userAccount = userAccountRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!userUpdateDTO.getName().equals("")) {
      userAccount.setName(userUpdateDTO.getName());
    }
    if (!userUpdateDTO.getEmail().equals("")) {
      userAccount.setEmail(userUpdateDTO.getEmail());
    }

    if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().equals("")) {
      userAccount.setPassword(userUpdateDTO.getPassword());
    }

    if (!userUpdateDTO.getRole().equals("")) {
      userAccount.setRole(userUpdateDTO.getRole());
    }
    return new ResponseEntity<>("User updated succesfully", HttpStatus.OK);
  }

  public ResponseEntity<List<UserDTO>> getAllUsers(Long userId) {
    UserAccount requestUser = userAccountRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    if (!requestUser.getRole().equals("admin")) {
      throw new UserNotAdminException("Only admin can acces this data");
    }
    return new ResponseEntity<>(userMapper.toUserDTO(userAccountRepository.findAll()),
        HttpStatus.OK);
  }


}
