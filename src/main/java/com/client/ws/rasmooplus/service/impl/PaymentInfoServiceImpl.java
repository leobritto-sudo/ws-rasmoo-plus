package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.model.UserPaymentInfo;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired private UserRepository userRepository;

    @Autowired private UserPaymentInfoRepository userPaymentInfoRepository;

    @Autowired private WsRaspayIntegration wsRaspayIntegration;

    @Override
    public Boolean process(PaymentProcessDTO paymentProcessDTO) {
        var userOpt = userRepository.findById(paymentProcessDTO.getUserPaymentInfoDTO().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Recurso não encontrado");
        }

        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Não é possível processar o pagamento pois usuário já possui assinatura");
        }

        CustomerDTO customerDTO = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));

        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(paymentProcessDTO.getUserPaymentInfoDTO(), user);
        userPaymentInfoRepository.save(userPaymentInfo);

        return null;
    }
}
