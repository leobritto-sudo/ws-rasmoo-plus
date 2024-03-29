package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserPaymentInfoDTO;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserPaymentInfo;

public class UserPaymentInfoMapper {
    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDTO userPaymentInfoDTO, User user) {

        return UserPaymentInfo.builder()
                .id(userPaymentInfoDTO.getId())
                .cardNumber(userPaymentInfoDTO.getCardNumber())
                .cardExpirationMonth(userPaymentInfoDTO.getCardExpirationMonth())
                .cardExpirationYear(userPaymentInfoDTO.getCardExpirationYear())
                .cardSecurityCode(userPaymentInfoDTO.getCardSecurityCode())
                .price(userPaymentInfoDTO.getPrice())
                .dtPayment(userPaymentInfoDTO.getDtPayment())
                .installments(Long.valueOf(userPaymentInfoDTO.getInstallments()))
                .user(user)
                .build();
    }
}
