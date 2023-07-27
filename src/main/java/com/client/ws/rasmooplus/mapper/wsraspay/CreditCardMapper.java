package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.UserPaymentInfoDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;

public class CreditCardMapper {
    public static CreditCardDTO build(UserPaymentInfoDTO userPaymentInfoDTO, String documentNumber) {
        return CreditCardDTO.builder()
                .documentNumber(documentNumber)
                .cvv(Integer.valueOf(userPaymentInfoDTO.getCardSecurityCode()))
                .number(userPaymentInfoDTO.getCardNumber())
                .month(Integer.valueOf(userPaymentInfoDTO.getCardExpirationMonth().toString()))
                .year(Integer.valueOf(userPaymentInfoDTO.getCardExpirationYear().toString()))
                .installments(userPaymentInfoDTO.getInstallments())
                .build();
    }
}
