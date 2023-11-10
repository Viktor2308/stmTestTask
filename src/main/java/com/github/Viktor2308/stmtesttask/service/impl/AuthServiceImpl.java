package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.dto.CreateUserDto;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtRequest;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtResponse;
import com.github.Viktor2308.stmtesttask.entity.RefreshToken;
import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.exception.AuthException;
import com.github.Viktor2308.stmtesttask.exception.RegistrationException;
import com.github.Viktor2308.stmtesttask.exception.TokenNotFoundException;
import com.github.Viktor2308.stmtesttask.exception.UserNotFoundException;
import com.github.Viktor2308.stmtesttask.mapper.UserMapper;
import com.github.Viktor2308.stmtesttask.repository.RefreshTokenRepository;
import com.github.Viktor2308.stmtesttask.security.JwtAuthentication;
import com.github.Viktor2308.stmtesttask.security.JwtProvider;
import com.github.Viktor2308.stmtesttask.service.AuthService;
import com.github.Viktor2308.stmtesttask.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


/**
 * Implementation AuthService interface
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RefreshTokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public void registrationUser(CreateUserDto user) {
        if (userService.getUserByLogin(user.getLogin()).isPresent()) {
            throw new RegistrationException("User with this login is already registered");
        }
        User newUser = userMapper.toUser(user);
        newUser.setRole(Role.USER);
        userService.saveNewUser(newUser);
    }

    @Override
    public JwtResponse login(@NonNull JwtRequest jwtRequest) {
        final User user = userService.getUserByLogin(jwtRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (BCrypt.checkpw(jwtRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            tokenRepository.save(new RefreshToken(user.getLogin(), refreshToken));
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password");
        }
    }

    @Override
    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final RefreshToken savedRefreshToken = tokenRepository.findById(login).orElseThrow(
                    () -> new TokenNotFoundException("Token not found"));
            if (savedRefreshToken.getRefreshToken().equals(refreshToken)) {
                final User user = userService.getUserByLogin(login).orElseThrow(
                        () -> new UserNotFoundException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }

        }
        return new JwtResponse(null, null);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final RefreshToken savedRefreshToken = tokenRepository.findById(login).orElseThrow(
                    () -> new TokenNotFoundException("Token not found"));
            if (savedRefreshToken.getRefreshToken().equals(refreshToken)) {
                final User user = userService.getUserByLogin(login).orElseThrow(
                        () -> new UserNotFoundException("User not found"));
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                final String accessToken = jwtProvider.generateAccessToken(user);
                tokenRepository.save(new RefreshToken(user.getLogin(), newRefreshToken));
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Token not valid");
    }

    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
