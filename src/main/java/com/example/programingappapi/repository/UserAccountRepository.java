package com.example.programingappapi.repository;


import java.util.ArrayList;
import java.util.Optional;

import com.example.programingappapi.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findById(Long id);

  UserAccount save (UserAccount userAccount);

  void deleteUserAccountById(Long id);

  ArrayList<UserAccount> findAll();
}
