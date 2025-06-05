package com.estore.service.impl;

import com.estore.config.JwtService;
import com.estore.dto.request.AuthRequest;
import com.estore.dto.response.AuthResponse;
import com.estore.model.User;
import com.estore.repository.UserRepository;
import com.estore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getSenha()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getSenha())
                .roles(user.getTipo().toString())
                .build();

        String jwt = jwtService.generateToken(userDetails);
        return new AuthResponse(jwt, user.getTipo());
    }
}
