package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.UserDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.model.redis.RecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.repository.redis.RecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RecoveryCodeRepository recoveryCodeRepository;

    @Override
    public User create(UserDTO userDTO) {
        if (Objects.nonNull(userDTO.getId())) {
            throw new BadRequestException("O Id deve ser nulo");
        }

        var userTypeOptional = userTypeRepository.findById(userDTO.getUserTypeId());

        if (userTypeOptional.isEmpty()) {
            throw new NotFoundException("Não foi encontrado o recurso");
        }

        UserType userType = userTypeOptional.get();

        User user = UserMapper.fromDtoToEntity(userDTO, userType, null);

        return userRepository.save(user);
    }

    @Override
    public Object sendRecoveryCode(String email) {
        String code = String.format("%4d", new Random().nextInt(10000));
        return recoveryCodeRepository.save(RecoveryCode.builder().code(code).build());
    }
}
