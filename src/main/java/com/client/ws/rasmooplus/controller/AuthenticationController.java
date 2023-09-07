package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDto loginDto) {
        UsernamePasswordAuthenticationToken userPassToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(userPassToken);
            String token = tokenService.getToken(authentication);

            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao  formatar token - " + e.getMessage());
        }
    }
}
