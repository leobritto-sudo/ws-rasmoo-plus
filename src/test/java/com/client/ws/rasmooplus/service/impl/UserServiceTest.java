package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.UserDTO;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenCreateWhenIdIsNullAndUserTypeFoundThenReturnUserCreated() {
        when(userTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getUserType()));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser(getUserDTO(), getUserType()));

        assertEquals(getUser(getUserDTO(), getUserType()), userService.create(getUserDTO()));
    }

    private UserDTO getUserDTO() {
        return UserDTO.builder()
                .cpf("12345678910")
                .phone("1112345678")
                .name("Jorge")
                .email("jorge@gmail.com")
                .userTypeId(1L)
                .build();
    }

    private UserType getUserType() {
        return new UserType(1L, "Aluno", "Aluno da plataforma");
    }

    private User getUser(UserDTO userDTO, UserType userType) {
        return User.builder()
                .id(1L)
                .cpf(userDTO.getCpf())
                .dtExpiration(userDTO.getDtExpiration())
                .dtSubscription(userDTO.getDtSubscription())
                .userType(userType)
                .build();
    }
}