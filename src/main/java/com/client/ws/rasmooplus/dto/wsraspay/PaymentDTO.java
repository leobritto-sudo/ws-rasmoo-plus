package com.client.ws.rasmooplus.dto.wsraspay;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private String customerId;

    private String orderId;

    private CreditCardDTO creditCard;
}
