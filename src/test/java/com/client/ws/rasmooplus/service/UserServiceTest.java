package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
        userService.sendRecoveryCode(null);
    }
}
