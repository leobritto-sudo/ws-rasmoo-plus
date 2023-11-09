package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    MailIntegration mailIntegration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isPresent()) {
            return userCredentialsOpt.get();
        }

        throw new NotFoundException("Usuário não encontrado");
    }

    @Override
    public void sendRecoveryCode(String email) {
        UserRecoveryCode userRecoveryCode;
        String code = String.format("%4d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {
            var user = userDetailsRepository.findByUsername(email);

            if (user.isEmpty()) {
                throw new NotFoundException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode(null, email, code, LocalDateTime.now());
        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
            userRecoveryCode.setCode(code);
            userRecoveryCode.setCreationDate(LocalDateTime.now());
        }

        mailIntegration.send(email, "Código de recuperação: " + code, "CÓDIGO DE RECUPERAÇÃO");
        userRecoveryCodeRepository.save(userRecoveryCode);
    }
}
