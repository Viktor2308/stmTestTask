package com.github.Viktor2308.stmtesttask.dto.jwtDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * JwtRequest - class object the user will send to receive a JWT token
 */
@Getter
@Setter
public class JwtRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
