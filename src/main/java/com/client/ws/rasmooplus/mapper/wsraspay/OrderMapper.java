package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;

public class OrderMapper {
    public static OrderDTO build(String customerId, PaymentProcessDTO paymentProcessDTO) {
        return OrderDTO.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDTO.getProductKey())
                .discount(paymentProcessDTO.getDiscount())
                .build();
    }
}
