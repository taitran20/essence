package com.tai.essence.api.controller.auth;

import com.tai.essence.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    /*@PostMapping("/signup")
    public ResponseEntity<AuthResponse> createdUserHandler(@RequestBody UserRequest userRequest) throws UserException {
        User user = authService.registerUser(userRequest);
        Cart cart = cartService.createCart(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token,"Signup Success");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }*/
}
