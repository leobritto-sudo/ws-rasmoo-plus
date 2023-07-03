package com.client.ws.rasmooplus.dto.wsraspay;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String customerId;

    private String orderId;

    private CreditCardDTO creditCard;
}
