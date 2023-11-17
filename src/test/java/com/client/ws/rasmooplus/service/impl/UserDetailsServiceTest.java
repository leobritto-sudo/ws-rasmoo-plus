package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private MailIntegration mailIntegration;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserCredentials userCredentials;

    private UserRecoveryCode userRecoveryCode;

    @BeforeEach
    void setup() {
        userCredentials = UserCredentials.builder()
                .id(1L)
                .username("rasmoo@gmail.com")
                .password("123456")
                .build();

        userRecoveryCode = UserRecoveryCode.builder()
                .id("wigdnjvi0203")
                .code("1234")
                .email("rasmoo@gmail.com")
                .build();
    }

    //Testes loadByUsername
    @Test
    void givenLoadUserByUsernameWhenUserCredentialsFoundThenReturnUserCredentials() {
        when(userDetailsRepository.findByUsername("rasmoo@gmail.com")).thenReturn(Optional.of(userCredentials));

        assertEquals(userCredentials, userDetailsService.loadUserByUsername("rasmoo@gmail.com"));

        verify(userDetailsRepository, times(1)).findByUsername("rasmoo@gmail.com");
    }

    @Test
    void givenLoadUserByUsernameWhenUserCredentialsNotFoundThenThrowNotFoundException() {
        when(userDetailsRepository.findByUsername("roger12@gmail.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.loadUserByUsername("roger12@gmail.com"));

        verify(userDetailsRepository, times(1)).findByUsername("roger12@gmail.com");
    }

    // Testes sendRecoveryCode
    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeFoundThenSaveNewRecoveryCode() {
        when(userRecoveryCodeRepository.findByEmail("rasmoo@gmail.com")).thenReturn(Optional.of(userRecoveryCode));

        userDetailsService.sendRecoveryCode("rasmoo@gmail.com");

        verify(userRecoveryCodeRepository, times(1)).findByEmail("rasmoo@gmail.com");
        verify(userDetailsRepository, times(0)).findByUsername("rasmoo@gmail.com");
        verify(mailIntegration, times(1)).send(eq("rasmoo@gmail.com"), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(1)).save(userRecoveryCode);
    }

    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeNotFoundAndUserCredentialsFoundThenSaveNewRecoveryCode() {
        when(userRecoveryCodeRepository.findByEmail("rasmoo@gmail.com")).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername("rasmoo@gmail.com")).thenReturn(Optional.of(userCredentials));

        userDetailsService.sendRecoveryCode("rasmoo@gmail.com");

        verify(userRecoveryCodeRepository, times(1)).findByEmail("rasmoo@gmail.com");
        verify(userDetailsRepository, times(1)).findByUsername("rasmoo@gmail.com");
        verify(mailIntegration, times(1)).send(eq("rasmoo@gmail.com"), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(1)).save(any(UserRecoveryCode.class));
    }

    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeNotFoundAndUserCredentialsNotFoundThenThrowNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail("roger12@gmail.com")).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername("roger12@gmail.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.sendRecoveryCode("roger12@gmail.com"));

        verify(userRecoveryCodeRepository, times(1)).findByEmail("roger12@gmail.com");
        verify(userDetailsRepository, times(1)).findByUsername("roger12@gmail.com");
        verify(mailIntegration, times(0)).send(eq("roger12@gmail.com"), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(0)).save(any(UserRecoveryCode.class));
    }
}