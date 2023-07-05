package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {
    private final RestTemplate restTemplate;

    private final HttpHeaders headers;
    public WsRaspayIntegrationImpl() {
        restTemplate = new RestTemplate();
        headers = getHttpHeaders();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        try {
            HttpEntity<CustomerDTO> request = new HttpEntity<>(customerDTO, this.headers);
            ResponseEntity<CustomerDTO> response =
                    restTemplate.exchange("http://localhost:8081/ws-raspay/v1/customer", HttpMethod.POST, request, CustomerDTO.class);

            return response.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Boolean processPayment(PaymentDTO paymentDTO) {
        return null;
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = "rasmooplus:r@sm00";
        String base64 = Base64.encodeBase64String(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }
}
