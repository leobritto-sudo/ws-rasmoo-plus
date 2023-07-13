package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;

public interface PaymentInfoService {
    Boolean process(PaymentProcessDTO paymentProcessDTO);
}
