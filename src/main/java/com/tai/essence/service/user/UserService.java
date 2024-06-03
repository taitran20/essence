package com.tai.essence.service.user;

import com.tai.essence.dto.UserDTO;
import com.tai.essence.exception.UserException;
import com.tai.essence.request.UserRequest;

public interface UserService {
    UserDTO registerUser(UserRequest userRequest) throws UserException;
}
