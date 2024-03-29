package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserDTO;
import com.client.ws.rasmooplus.model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;

public class UserMapper {
    public static User fromDtoToEntity(UserDTO userDTO, UserType userType, SubscriptionType subscriptionType) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .cpf(userDTO.getCpf())
                .dtExpiration(userDTO.getDtExpiration())
                .dtSubscription(userDTO.getDtSubscription())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();
    }
}
