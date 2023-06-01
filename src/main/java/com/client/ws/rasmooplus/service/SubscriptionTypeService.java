package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.model.SubscriptionType;

import java.util.List;

public interface SubscriptionTypeService {
    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDTO subscriptionTypeDTO);

    SubscriptionType update(Long id, SubscriptionTypeDTO subscriptionTypeDTO);

    void delete(Long id);
}
