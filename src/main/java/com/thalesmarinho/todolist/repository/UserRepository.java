package com.thalesmarinho.todolist.repository;

import com.thalesmarinho.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByUsername(String username);
}