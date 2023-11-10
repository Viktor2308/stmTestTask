package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserByLoginTest() {
        User user = new User();
        user.setLogin("testLogin");

        when(userRepository.findByLogin("testLogin")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByLogin("testLogin");

        assertEquals("testLogin", result.get().getLogin());
        verify(userRepository, times(1)).findByLogin("testLogin");
    }

    @Test
    public void saveNewUserTest() {
        User user = new User();
        user.setLogin("testLogin");

        userService.saveNewUser(user);

        verify(userRepository, times(1)).save(user);
    }

}