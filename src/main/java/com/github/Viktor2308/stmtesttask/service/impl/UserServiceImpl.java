package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.repository.UserRepository;
import com.github.Viktor2308.stmtesttask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void saveNewUser(User newUser) {
        userRepository.save(newUser);
    }
}
