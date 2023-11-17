package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.UserDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;

    private UserType userType;

    private User user;

    @BeforeEach
    void setup() {
        userDTO = UserDTO.builder()
                .cpf("12345678910")
                .email("jorge@gmail.com")
                .userTypeId(1L)
                .build();

        userType = new UserType(1L, "Aluno", "Aluno da plataforma");

        user = User.builder()
                .cpf(userDTO.getCpf())
                .email("jorge@gmail.com")
                .dtExpiration(userDTO.getDtExpiration())
                .dtSubscription(userDTO.getDtSubscription())
                .userType(userType)
                .build();
    }

    @Test
    void givenCreateWhenIdIsNullAndUserTypeFoundThenReturnUserCreated() {
        when(userTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userType));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        assertEquals(user, userService.create(userDTO));

        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenCreateWhenIdIsNotNullThenThrowBadRequest() {
        userDTO.setId(1L);
        assertThrows(BadRequestException.class, () -> userService.create(userDTO));

        verify(userTypeRepository, times(0)).findById(Mockito.anyLong());
        verify(userRepository, times(0)).save(Mockito.any(User.class));
    }

    @Test
    void givenCreateWhenUserTypeNotFoundThenNotFoundException() {
        when(userTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.create(userDTO));

        verify(userTypeRepository, times(1)).findById(Mockito.anyLong());
        verify(userRepository, times(0)).save(Mockito.any(User.class));
    }
}