package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.dto.CreateUserDto;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtRequest;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtResponse;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtRefreshRequest;
import com.github.Viktor2308.stmtesttask.exception.RegistrationException;
import com.github.Viktor2308.stmtesttask.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registration new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Error: Bad Request")})
    @PostMapping("/reg")
    ResponseEntity<?> registration(@RequestBody @Valid CreateUserDto user) {
        try {
            authService.registrationUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@RequestBody @Valid JwtRequest jwtRequest){
        final JwtResponse jwtResponse = authService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping("/token")
    ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody @Valid JwtRefreshRequest refreshRequest){
        final JwtResponse jwtResponse = authService.getAccessToken(refreshRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping("/refresh")
    ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody @Valid JwtRefreshRequest refreshRequest){
        final JwtResponse newToken = authService.refresh(refreshRequest.getRefreshToken());
        return ResponseEntity.ok(newToken);
    }
}