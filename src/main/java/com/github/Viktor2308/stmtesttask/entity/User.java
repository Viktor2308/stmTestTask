package com.github.Viktor2308.stmtesttask.entity;

import lombok.*;

import java.util.Set;
import java.util.UUID;
/**
 User – the entity purchasing tickets.
 Attributes: id, login, password, full name, list of roles.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User{
    /**
     * UUID id - unique identifier
     */
    private UUID id;
    /**
     * String login - unique user login
     */
    private String login;
    /**
     * String password - encoded password
     */
    private String password;
    /**
     * String fullName - full username
     */
    private String fullName;
    /**
     * User role
     * @see Role
     */
    private Role role;
}
