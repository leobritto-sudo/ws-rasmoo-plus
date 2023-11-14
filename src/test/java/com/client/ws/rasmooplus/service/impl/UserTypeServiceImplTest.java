package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceImplTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    @Test
    void testFindAll() {
        List<UserType> userTypeList = new ArrayList<>();
        UserType userType1 = new UserType(1L, "Professor", "Professor da plataforma");
        UserType userType2 = new UserType(2L, "Administrador", "Funcionário");

        userTypeList.add(userType1);
        userTypeList.add(userType2);

        Mockito.when(userTypeRepository.findAll()).thenReturn(userTypeList);
        Assertions.assertFalse(userTypeService.findAll().isEmpty());
        Assertions.assertEquals(2, userTypeService.findAll().size());
    }
}