package com.example.programingappapi.mapper;


import com.example.programingappapi.dto.UserDTO;
import com.example.programingappapi.dto.UserScoreDTO;
import com.example.programingappapi.entity.UserAccount;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserMapper {

    public UserDTO toUserDTO(UserAccount userAccount) {
        return UserDTO.builder().id(userAccount.getId())
                .role(userAccount.getRole()).email(userAccount.getEmail()).name(userAccount.getName())
                .build();
    }

    public List<UserDTO> toUserDTO(List<UserAccount> userAccounts) {
        List<UserDTO> userDTOS = new ArrayList<>();
        userAccounts.stream().forEach(u -> userDTOS.add(toUserDTO(u)));
        return userDTOS;
    }

    public UserScoreDTO userScoreDTO(UserAccount userAccount, Long score)
    {
        return UserScoreDTO.builder()
                .score(score)
                .profilePicture(userAccount.getProfilePicture())
                .email(userAccount.getEmail())
                .name(userAccount.getName())
                .build();
    }


}
