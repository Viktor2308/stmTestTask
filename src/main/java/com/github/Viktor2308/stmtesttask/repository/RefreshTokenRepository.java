package com.github.Viktor2308.stmtesttask.repository;

import com.github.Viktor2308.stmtesttask.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}