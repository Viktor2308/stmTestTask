package com.github.Viktor2308.stmtesttask.repository.impl;


import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.initializer.Postgres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql("/insert-test-data.sql")
@SpringBootTest
@ContextConfiguration(initializers = {
        Postgres.initializer.class
})
@Transactional
class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @BeforeAll
    static void init(){
        Postgres.CONTAINER.start();
    }

    @AfterAll
    static void close(){
        Postgres.CONTAINER.close();
    }
    @Test
    public void saveTest() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setFullName("testFullName");
        user.setRole(Role.USER);

        int result = userRepository.save(user);

        assertEquals(1, result);
    }


    @Test
    void findByLogin() {
       User user = userRepository.findByLogin("test_user_login").get();

       assertEquals("test_user_login", user.getLogin());
    }

    @Test
    void findAll() {
        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
        System.out.println(userList);
    }
}