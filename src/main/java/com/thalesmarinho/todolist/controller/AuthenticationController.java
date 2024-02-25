package com.thalesmarinho.todolist.controller;

import com.thalesmarinho.todolist.dto.LoginDto;
import com.thalesmarinho.todolist.dto.LoginResponseDto;
import com.thalesmarinho.todolist.dto.UserDto;
import com.thalesmarinho.todolist.exception.EmailAlreadyUsedException;
import com.thalesmarinho.todolist.exception.UsernameAlreadyUsedException;
import com.thalesmarinho.todolist.model.User;
import com.thalesmarinho.todolist.repository.UserRepository;
import com.thalesmarinho.todolist.security.TokenService;
import com.thalesmarinho.todolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (data.username(), data.password());
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto data) {
        String username = data.getUsername().toLowerCase();
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new UsernameAlreadyUsedException();
        });

        String email = data.getEmail().toLowerCase();
        userRepository.findByEmailIgnoreCase(email).ifPresent(existingUser -> {
            throw new EmailAlreadyUsedException();
        });

        return new ResponseEntity<>(userService.registerUser(data), HttpStatus.CREATED);
    }
}