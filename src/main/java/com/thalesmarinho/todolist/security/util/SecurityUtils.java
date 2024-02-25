package com.thalesmarinho.todolist.security.util;

import com.thalesmarinho.todolist.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<User> getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if(authentication.getPrincipal() instanceof Optional<?> optional)
                        return (User) optional.orElse(null);

                    return null;
                });
    }
}