package com.github.Viktor2308.stmtesttask.security;

import com.github.Viktor2308.stmtesttask.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * JwtProvider class generates and validates access and refresh tokens.
 */
@Slf4j
@Component
public class JwtProvider {
    /**
     * The secret key is set in the application.properties
     */
    private final SecretKey jwtSecret;

    public JwtProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Method generate access token, default time expiration 10 min
     *
     * @param user - current user
     * @return access token, type 'String' with claims "roles" and "userLogin"
     */
    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(jwtSecret)
                .claim("roles", List.of(user.getRole()))
                .claim("fullName", user.getFullName())
                .compact();
    }

    /**
     * Method generate refresh token, default time expiration 30 days
     *
     * @param user - current user
     * @return refresh token, type 'String' with claims userLogin
     */
    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(refreshExpiration)
                .signWith(jwtSecret)
                .compact();
    }

    /**
     * Method for validate access token
     *
     * @param accessToken access token
     * @return true if token valid else false
     */
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken);
    }

    /**
     * Method for validate refresh token
     *
     * @param refreshToken refresh token
     * @return true if token valid else false
     */
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken);
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /**
     * Get Claims from jwt access token
     *
     * @param token jwt access token
     * @return access token claims roles and userLogin
     */
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token);
    }

    /**
     * Get Claims from jwt refresh token
     *
     * @param token jwt refresh token
     * @return refresh token claims
     */
    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token);
    }

    private Claims getClaims(@NonNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
