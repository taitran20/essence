package com.tai.essence.service.user;

import com.tai.essence.dto.UserDTO;
import com.tai.essence.entity.User;
import com.tai.essence.exception.UserException;
import com.tai.essence.repository.UserRepository;
import com.tai.essence.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDTO registerUser(UserRequest userRequest) throws UserException {
        User user = userRepository.findByEmail(userRequest.getEmail());
        if(user == null){
            throw new UserException("Email is already used!");
        }
        User savedUser = new User();
        savedUser.setEmail(userRequest.getEmail());
        savedUser.setPassword(userRequest.getPassword());
        savedUser.setFirst_Name(userRequest.getFirst_Name());
        savedUser.setLast_Name(userRequest.getLast_Name());
        savedUser.setCreatedAt(LocalDateTime.now());
        UserDTO userDTO =  modelMapper.map(userRepository.save(savedUser), UserDTO.class);
        return userDTO;
    }
}
