package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.exception.UserNotFoundException;
import com.github.Viktor2308.stmtesttask.mapper.UserPrincipalMapper;
import com.github.Viktor2308.stmtesttask.repository.UserRepository;
import com.github.Viktor2308.stmtesttask.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPrincipalMapper principalMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(userLogin).orElseThrow(()
                -> new UserNotFoundException("User not found"));
        return principalMapper.toUserPrincipal(user);
    }
}
