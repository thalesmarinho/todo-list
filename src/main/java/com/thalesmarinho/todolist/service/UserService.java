package com.thalesmarinho.todolist.service;

import com.thalesmarinho.todolist.dto.RegisterDto;
import com.thalesmarinho.todolist.model.User;
import com.thalesmarinho.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegisterDto data) {
        User user = new User();

        user.setUsername(data.getUsername().toLowerCase());

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        user.setPassword(encryptedPassword);

        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());

        if(data.getEmail() != null)
            user.setEmail(data.getEmail().toLowerCase());

        user.setRole(data.getRole());

        userRepository.save(user);

        return user;
    }
}