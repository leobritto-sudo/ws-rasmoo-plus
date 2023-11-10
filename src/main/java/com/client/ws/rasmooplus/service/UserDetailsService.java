package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    UserDetails loadUserByUsername(String username);

    void sendRecoveryCode(String email);

    Boolean recoveryCodeIsValid(String code, String email);

    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
