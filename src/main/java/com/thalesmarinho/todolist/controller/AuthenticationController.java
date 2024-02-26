package com.thalesmarinho.todolist.controller;

import com.thalesmarinho.todolist.dto.LoginDto;
import com.thalesmarinho.todolist.dto.LoginResponseDto;
import com.thalesmarinho.todolist.dto.UserDto;
import com.thalesmarinho.todolist.exception.AccountResourceException;
import com.thalesmarinho.todolist.exception.EmailAlreadyUsedException;
import com.thalesmarinho.todolist.exception.UsernameAlreadyUsedException;
import com.thalesmarinho.todolist.model.User;
import com.thalesmarinho.todolist.repository.UserRepository;
import com.thalesmarinho.todolist.security.TokenService;
import com.thalesmarinho.todolist.service.MailService;
import com.thalesmarinho.todolist.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;
    private final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (data.username(), data.password());
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDto data, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        String username = data.getUsername().toLowerCase();
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new UsernameAlreadyUsedException();
        });

        String email = data.getEmail().toLowerCase();
        userRepository.findByEmailIgnoreCase(email).ifPresent(existingUser -> {
            throw new EmailAlreadyUsedException();
        });

        User user = userService.registerUser(data);
        mailService.sendActivationEmail(user, getSiteURL(request));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public void verify(@Param("activationKey") String activationKey) {
        Optional<User> user = userService.verifyUser(activationKey);

        if(user.isEmpty())
            throw new AccountResourceException("No user was found for this activation key");
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}