package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;

public class PaymentMapper {
    public static PaymentDTO build(String customerId, String orderId, CreditCardDTO creditCardDTO) {
        return PaymentDTO.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(creditCardDTO)
                .build();
    }
}
