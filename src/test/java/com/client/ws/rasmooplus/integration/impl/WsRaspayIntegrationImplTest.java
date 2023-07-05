package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

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

    @Test
    void createOrderWhenOK() {
        OrderDTO orderDTO = new OrderDTO(null, "64a569d63faf0713c4acc2dc", BigDecimal.ZERO, "MONTH22");
        wsRaspayIntegration.createOrder(orderDTO);
    }

}