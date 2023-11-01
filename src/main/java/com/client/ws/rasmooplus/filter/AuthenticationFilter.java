package com.client.ws.rasmooplus.filter;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserDetailsRepository userDetailsRepository;

    public AuthenticationFilter(TokenService tokenService, UserDetailsRepository userDetailsRepository) {
        this.tokenService = tokenService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);

        if (Boolean.TRUE.equals(tokenService.isValid(token))) {
            authByToken(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authByToken(String token) {
        Long userId = tokenService.getUserId(token);
        Optional<UserCredentials> userOpt = userDetailsRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado");
        }

        UserCredentials userCredentials = userOpt.get();

        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(userCredentials, null, userCredentials.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

    private String getBearerToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (Objects.isNull(token) || !token.startsWith("Bearer")) {
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
