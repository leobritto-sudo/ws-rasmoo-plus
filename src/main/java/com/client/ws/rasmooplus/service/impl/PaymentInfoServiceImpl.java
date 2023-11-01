package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import com.client.ws.rasmooplus.enums.UserTypeEnum;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.jpa.*;
import com.client.ws.rasmooplus.repository.jpa.*;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired private UserRepository userRepository;

    @Autowired private UserPaymentInfoRepository userPaymentInfoRepository;

    @Autowired private WsRaspayIntegration wsRaspayIntegration;

    @Autowired private MailIntegration mailIntegration;

    @Autowired private UserDetailsRepository userDetailsRepository;

    @Autowired private UserTypeRepository userTypeRepository;

    @Autowired private SubscriptionTypeRepository subscriptionTypeRepository;

    @Value("${webservices.rasplus.default.password}")
    private String defaultPass;

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

        // Cria customer
        CustomerDTO customerDTO = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));

        // Processa pagamento
        PaymentDTO paymentDTO = getPaymentDTO(paymentProcessDTO, customerDTO);

        return createCredentials(paymentProcessDTO, user, paymentDTO);
    }

    private boolean createCredentials(PaymentProcessDTO paymentProcessDTO, User user, PaymentDTO paymentDTO) {
        if (Boolean.TRUE.equals(wsRaspayIntegration.processPayment(paymentDTO))) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(paymentProcessDTO.getUserPaymentInfoDTO(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            Optional<UserType> userTypeOptional = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if (userTypeOptional.isEmpty()) {
                throw new NotFoundException("UserType não encontrado");
            }

            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), new BCryptPasswordEncoder().encode(defaultPass), userTypeOptional.get());
            userDetailsRepository.save(userCredentials);

            Optional<SubscriptionType> subscriptionTypeOpt = subscriptionTypeRepository.findByProductKey(paymentProcessDTO.getProductKey());

            if (subscriptionTypeOpt.isEmpty()) {
                throw new NotFoundException("SubscriptionType não encontrado");
            }

            user.setSubscriptionType(subscriptionTypeOpt.get());
            userRepository.save(user);

            sendEmail(user);

            return true;
        }
        return false;
    }

    private void sendEmail(User user) {
        String message = "Parabéns, seu acesso foi liberado\n\n" +
                "Seguem seus dados para entrar na plataforma\n" +
                "Usuário: " + user.getEmail() + "\n" +
                "Senha: " + defaultPass;
        mailIntegration.send(user.getEmail(), message, "ACESSO LIBERADO!");
    }

    private PaymentDTO getPaymentDTO(PaymentProcessDTO paymentProcessDTO, CustomerDTO customerDTO) {
        OrderDTO orderDTO = wsRaspayIntegration.createOrder(OrderMapper.build(customerDTO.getId(), paymentProcessDTO));

        return PaymentMapper.build(
                customerDTO.getId(),
                orderDTO.getId(),
                CreditCardMapper.build(paymentProcessDTO.getUserPaymentInfoDTO(), customerDTO.getCpf()));
    }
}
