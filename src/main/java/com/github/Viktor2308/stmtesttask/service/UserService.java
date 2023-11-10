package com.github.Viktor2308.stmtesttask.service;

import com.github.Viktor2308.stmtesttask.entity.User;

import java.util.Optional;

/**
 * Interface for operation with users
 * @see User
 */
public interface UserService {
    Optional<User> getUserByLogin(String login);

    void saveNewUser(User newUser);
}
