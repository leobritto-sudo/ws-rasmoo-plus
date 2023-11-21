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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private final static String USERNAME_VALID = "rasmoo@gmail.com";
    private final static String USERNAME_NOT_VALID = "roger12@gmail.com";

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
                .creationDate(LocalDateTime.now())
                .build();
    }

    //Testes loadByUsername
    @Test
    void givenLoadUserByUsernameWhenUserCredentialsFoundThenReturnUserCredentials() {
        when(userDetailsRepository.findByUsername(USERNAME_VALID)).thenReturn(Optional.of(userCredentials));

        assertEquals(userCredentials, userDetailsService.loadUserByUsername(USERNAME_VALID));

        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_VALID);
    }

    @Test
    void givenLoadUserByUsernameWhenUserCredentialsNotFoundThenThrowNotFoundException() {
        when(userDetailsRepository.findByUsername(USERNAME_NOT_VALID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME_NOT_VALID));

        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_NOT_VALID);
    }

    // Testes sendRecoveryCode
    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeFoundThenSaveNewRecoveryCode() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_VALID)).thenReturn(Optional.of(userRecoveryCode));

        userDetailsService.sendRecoveryCode(USERNAME_VALID);

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_VALID);
        verify(userDetailsRepository, times(0)).findByUsername(USERNAME_VALID);
        verify(mailIntegration, times(1)).send(eq(USERNAME_VALID), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(1)).save(userRecoveryCode);
    }

    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeNotFoundAndUserCredentialsFoundThenSaveNewRecoveryCode() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_VALID)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_VALID)).thenReturn(Optional.of(userCredentials));

        userDetailsService.sendRecoveryCode(USERNAME_VALID);

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_VALID);
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_VALID);
        verify(mailIntegration, times(1)).send(eq(USERNAME_VALID), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(1)).save(any(UserRecoveryCode.class));
    }

    @Test
    void givenSendRecoveryCodeWhenRecoveryCodeNotFoundAndUserCredentialsNotFoundThenThrowNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_NOT_VALID)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_NOT_VALID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.sendRecoveryCode(USERNAME_NOT_VALID));

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_NOT_VALID);
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_NOT_VALID);
        verify(mailIntegration, times(0)).send(eq(USERNAME_NOT_VALID), anyString(), anyString());
        verify(userRecoveryCodeRepository, times(0)).save(any(UserRecoveryCode.class));
    }

    // Testes recoveryCodeIsValid
    @Test
    void givenRecoveryCodeIsValidWhenUserFoundThenReturnTrue() {
        ReflectionTestUtils.setField(userDetailsService, "minutesTimeout", 5);
        when(userRecoveryCodeRepository.findByEmail(USERNAME_VALID)).thenReturn(Optional.of(userRecoveryCode));

        assertTrue(userDetailsService.recoveryCodeIsValid("1234", USERNAME_VALID));

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_VALID);
    }

    @Test
    void givenRecoveryCodeIsValidWhenUserFoundAndGotTimeoutThenReturnFalse() {
        ReflectionTestUtils.setField(userDetailsService, "minutesTimeout", 5);
        userRecoveryCode.setCreationDate(LocalDateTime.of(2023, 11, 21, 9, 0));
        when(userRecoveryCodeRepository.findByEmail(USERNAME_VALID)).thenReturn(Optional.of(userRecoveryCode));

        assertFalse(userDetailsService.recoveryCodeIsValid("1234", USERNAME_VALID));

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_VALID);
    }

    @Test
    void givenRecoveryCodeIsValidWhenUserNotFoundThenThrowNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_NOT_VALID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userDetailsService.recoveryCodeIsValid("1234", USERNAME_NOT_VALID));

        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_NOT_VALID);
    }
}