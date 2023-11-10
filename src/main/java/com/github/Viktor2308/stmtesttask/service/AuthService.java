package com.github.Viktor2308.stmtesttask.service;

import com.github.Viktor2308.stmtesttask.dto.CreateUserDto;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtRequest;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtResponse;
import com.github.Viktor2308.stmtesttask.security.JwtAuthentication;

/**
 * Interface for registration, authentication and authorization users
 */
public interface AuthService {
    /**
     * Method for Registration new user in app
     * @param user new user
     * @return uuid new users
     * @see CreateUserDto
     */
    void registrationUser(CreateUserDto user);

    /**
     * Method for login users in system
     * @param jwtRequest login and password users
     * @return jwtResponse access and refresh tokens
     * @see JwtRequest
     * @see JwtResponse
     */
    JwtResponse login(JwtRequest jwtRequest);

    /**
     * Method for get access token by refresh token
     * @param refreshToken refresh token
     * @return jwtResponse with new access token only
     * @see JwtResponse
     */
    JwtResponse getAccessToken(String refreshToken);

    /**
     * Method for refresh refreshToken
     * @param refreshToken - refreshToken
     * @return jwtResponse with access token and new refresh token
     * @see JwtResponse
     */
    JwtResponse refresh(String refreshToken);

    /**
     * Method for get info from security context
     * @return JwtAuthentication
     * @see JwtAuthentication
     */
    JwtAuthentication getAuthInfo();

}
