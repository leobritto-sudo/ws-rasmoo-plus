package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDTO;
import com.client.ws.rasmooplus.model.jpa.User;

public interface UserService {
    User create(UserDTO userDTO);
}
