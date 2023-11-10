package com.github.Viktor2308.stmtesttask.dto.jwtDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JwtResponse - the class object user return in response to the JwtRequest.
 *
 * @see JwtRequest
 */
@Getter
@AllArgsConstructor
public class JwtResponse {
    /**
     * Constant type - 'Bearer'
     */
    private final String type ="Bearer";
    private String accessToken;
    private String refreshToken;
}
