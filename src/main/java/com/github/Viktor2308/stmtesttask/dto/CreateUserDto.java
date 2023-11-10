package com.github.Viktor2308.stmtesttask.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotEmpty
    @Size(min=2)
    private String login;
    @NotEmpty
    @Size(min=3)
    private String password;
    @NotEmpty
    @Size(min=2)
    private String fullName;
}
