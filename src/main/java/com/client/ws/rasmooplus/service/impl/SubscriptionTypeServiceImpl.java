package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {
    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    @Override
    @Cacheable(value = "subscriptionType")
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    @Cacheable(value = "subscriptionType", key = "#id")
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id);
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType create(SubscriptionTypeDTO subscriptionTypeDTO) {
        if (Objects.nonNull(subscriptionTypeDTO.getId())) {
            throw new BadRequestException("O Id deve ser nulo");
        }
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(subscriptionTypeDTO));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType update(Long id, SubscriptionTypeDTO subscriptionTypeDTO) {
        getSubscriptionType(id);
        subscriptionTypeDTO.setId(id);
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(subscriptionTypeDTO));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> subscriptionType = subscriptionTypeRepository.findById(id);
        if (subscriptionType.isPresent()) {
            return subscriptionType.get();
        }
        throw new NotFoundException("Recurso não encontrado");
    }
}
