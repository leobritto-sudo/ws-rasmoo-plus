package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subscription-type")
public class SubscriptionTypeController {

    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    @GetMapping
    public ResponseEntity<List<SubscriptionType>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable Long id) {
        SubscriptionType subscriptionType = subscriptionTypeService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionType);
    }

    @PostMapping
    public ResponseEntity<SubscriptionType> create(@Valid @RequestBody SubscriptionTypeDTO subscriptionTypeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(subscriptionTypeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionType> update(@PathVariable("id") Long id, @RequestBody SubscriptionTypeDTO subscriptionTypeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.update(id, subscriptionTypeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
