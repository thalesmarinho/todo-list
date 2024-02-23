package com.thalesmarinho.todolist.security;

import com.thalesmarinho.todolist.model.User;
import com.thalesmarinho.todolist.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recoverToken(request);

        if(token != null) {
            String username = tokenService.validateToken(token);
            Optional<User> user = userRepository.findByUsername(username);

            if(user.isEmpty()) throw new UsernameNotFoundException(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                    (user, null, user.get().getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}