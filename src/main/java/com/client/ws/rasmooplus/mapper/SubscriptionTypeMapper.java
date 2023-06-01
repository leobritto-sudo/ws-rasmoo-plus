package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.model.SubscriptionType;

public class SubscriptionTypeMapper {
    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDTO subscriptionTypeDTO) {
        SubscriptionType subscriptionType = SubscriptionType.builder()
                .id(subscriptionTypeDTO.getId())
                .name(subscriptionTypeDTO.getName())
                .accessMonth(subscriptionTypeDTO.getAccessMonth())
                .price(subscriptionTypeDTO.getPrice())
                .productKey(subscriptionTypeDTO.getProductKey())
                .build();

        return subscriptionType;
    }
}
