package com.client.ws.rasmooplus.integration;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDTO;
import com.client.ws.rasmooplus.integration.impl.WsRaspayIntegrationImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    private static HttpHeaders headers;

    private CustomerDTO customerDTO;

    @BeforeAll
    static void setupAll() {
        headers = getHttpHeaders();
    }

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "http://localhost:8080");
        customerDTO = new CustomerDTO();
    }

    @Test
    void givenCreateCustomerWhenApiResponseIs201ThenReturnCustomerDto() {
        customerDTO.setCpf("12345678910");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/customer");
        HttpEntity<CustomerDTO> request = new HttpEntity<>(customerDTO, headers);
        when(restTemplate.exchange("http://localhost:8080".concat("/customer"), HttpMethod.POST, request, CustomerDTO.class))
                .thenReturn(ResponseEntity.of(Optional.of(customerDTO)));

        wsRaspayIntegration.createCustomer(customerDTO);

        verify(restTemplate, times(1))
                .exchange("http://localhost:8080".concat("/customer"), HttpMethod.POST, request, CustomerDTO.class);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credentials = "rasmooplus:r@sm00";
        String base64 = Base64.encodeBase64String(credentials.getBytes());
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }
}