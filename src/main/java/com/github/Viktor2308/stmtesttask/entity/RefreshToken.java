package com.github.Viktor2308.stmtesttask.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@RedisHash("Token")
public class RefreshToken {
    @Id
    private String userLogin;
    private String refreshToken;
}
