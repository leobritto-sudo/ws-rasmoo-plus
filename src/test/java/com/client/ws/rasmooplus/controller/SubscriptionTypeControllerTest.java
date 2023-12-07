package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class SubscriptionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void givenFindAllThenReturnAllSubscriptionTypes() throws Exception {
        mockMvc.perform(get("/subscription-type"))
                .andExpect(status().isOk());
    }

    @Test
    void givenFindByIdWhenIdIs2ThenReturnSubscriptionTypeCorresponding() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997), "4EVER23");
        Mockito.when(subscriptionTypeService.findById(2L)).thenReturn(subscriptionType);

        mockMvc.perform(get("/subscription-type/2"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(status().isOk());
    }
}