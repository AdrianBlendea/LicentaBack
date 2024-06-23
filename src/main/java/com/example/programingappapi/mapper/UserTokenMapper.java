package com.example.programingappapi.mapper;


import com.example.programingappapi.dto.UserTokenDTO;
import com.example.programingappapi.entity.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserTokenMapper {

    public UserTokenDTO toUserTokenDTO(UserAccount userAccount, String token) {
        return UserTokenDTO.builder().id(userAccount.getId()).role(userAccount.getRole())
                .email(userAccount.getEmail()).name(userAccount.getName()).token(token).build();
    }
}
