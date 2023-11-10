package com.github.Viktor2308.stmtesttask.dto.jwtDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequest {
    @NotBlank
    private String refreshToken;
}
