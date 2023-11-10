package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.exception.UserNotFoundException;
import com.github.Viktor2308.stmtesttask.mapper.UserPrincipalMapper;
import com.github.Viktor2308.stmtesttask.repository.UserRepository;
import com.github.Viktor2308.stmtesttask.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPrincipalMapper principalMapper;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadUserByUsernameTest() {
        User user = new User();
        user.setLogin("testLogin");

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setLogin("testLogin");

        when(userRepository.findByLogin("testLogin")).thenReturn(Optional.of(user));
        when(principalMapper.toUserPrincipal(user)).thenReturn(userPrincipal);

        UserDetails result = userDetailsService.loadUserByUsername("testLogin");

        assertEquals("testLogin", result.getUsername());
        verify(userRepository, times(1)).findByLogin("testLogin");
        verify(principalMapper, times(1)).toUserPrincipal(user);
    }

    @Test
    public void loadUserByUsernameNotFoundTest() {
        when(userRepository.findByLogin("testLogin")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userDetailsService.loadUserByUsername("testLogin"));

        verify(userRepository, times(1)).findByLogin("testLogin");
        verify(principalMapper, times(0)).toUserPrincipal(any(User.class));
    }
}