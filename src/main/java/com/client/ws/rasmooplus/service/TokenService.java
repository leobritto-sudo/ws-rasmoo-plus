package com.client.ws.rasmooplus.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String getToken(Authentication authentication);

    Boolean isValid(String token);
}
