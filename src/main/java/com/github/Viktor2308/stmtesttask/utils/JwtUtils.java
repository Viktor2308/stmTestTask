package com.github.Viktor2308.stmtesttask.utils;

import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.security.JwtAuthentication;
import com.github.Viktor2308.stmtesttask.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        UserPrincipal principal = new UserPrincipal();
        principal.setLogin(claims.getSubject());
        principal.setAuthorities(getRoles(claims));
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setPrincipal(principal);
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
