package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.service.ShippingCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShippingCompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingCompanyService shippingCompanyService;
    @Test
    void createShippingCompany() {
    }

    @Test
    void editShippingCompany() {
    }

    @Test
    void deleteShippingCompany() {
    }
}