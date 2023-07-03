package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    @Test
    void createCustomerWhenOK() {
        CustomerDTO customerDTO = new CustomerDTO(null, "43466207010", "teste@teste.com", "Leonardo", "Alves");
        wsRaspayIntegration.createCustomer(customerDTO);
    }

}