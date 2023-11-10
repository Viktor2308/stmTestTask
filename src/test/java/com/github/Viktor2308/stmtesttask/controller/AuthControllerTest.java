package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.dto.CreateUserDto;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtRequest;
import com.github.Viktor2308.stmtesttask.dto.jwtDto.JwtResponse;
import com.github.Viktor2308.stmtesttask.exception.AuthException;
import com.github.Viktor2308.stmtesttask.exception.RegistrationException;
import com.github.Viktor2308.stmtesttask.exception.UserNotFoundException;
import com.github.Viktor2308.stmtesttask.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    public void registrationTestPositive() throws Exception {
        CreateUserDto user = new CreateUserDto();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setFullName("testFullName");

        Mockito.doNothing().when(authService).registrationUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void registrationTestNegative() throws Exception {
        CreateUserDto user = new CreateUserDto();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setFullName("testFullName");

        Mockito.doThrow(new RegistrationException("")).when(authService).registrationUser(any(CreateUserDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registrationTestInvalidData() throws Exception {
        CreateUserDto user = new CreateUserDto();
        user.setLogin("t");
        user.setPassword("t");
        user.setFullName("t");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginTestPositive() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("testLogin");
        jwtRequest.setPassword("testPassword");

        JwtResponse jwtResponse = new JwtResponse("testToken", "TestToken");

        Mockito.when(authService.login(jwtRequest)).thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginTestNegativeUserNotFound() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("testLogin");
        jwtRequest.setPassword("testPassword");

        Mockito.when(authService.login(any(JwtRequest.class))).thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginTestNegativeTokenNoCorrect() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("testLogin");
        jwtRequest.setPassword("testPassword");

        Mockito.when(authService.login(any(JwtRequest.class))).thenThrow(AuthException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void loginTestInvalidData() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin("");
        jwtRequest.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }
}