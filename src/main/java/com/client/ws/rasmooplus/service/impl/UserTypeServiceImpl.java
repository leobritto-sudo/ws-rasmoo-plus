package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public List<UserType> findAll() {
        List<UserType> userType = userTypeRepository.findAll();

        if (userType.isEmpty()) {
            throw new NotFoundException("Recurso não encontrado");
        }

        return userType;
    }
}
