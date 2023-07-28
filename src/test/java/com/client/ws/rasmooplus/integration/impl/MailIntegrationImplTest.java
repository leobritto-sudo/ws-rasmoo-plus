package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import com.client.ws.rasmooplus.integration.MailIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class MailIntegrationImplTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void createCustomerWhenOK() {
        mailIntegration.send("littocraft@gmail.com", "Olá Leo!", "Acesso liberado");
    }
}