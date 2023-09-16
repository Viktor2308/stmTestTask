package com.github.Viktor2308.stmtesttask.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/*
Пользователь – субъект, осуществляющий покупку билетов.
Атрибуты: логин, пароль, ФИО.
 */
@Getter
@Setter
@Builder
public class User{
    private UUID id;
    private String login;
    private String password;
    private String username;
    private String lastName;
    private String surname;
}
