package com.github.Viktor2308.stmtesttask.repository;

import com.github.Viktor2308.stmtesttask.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    int save(User user);
    int update(User user);
    Optional<User> findByLogin(String login);

    List<User> findAll();
}
