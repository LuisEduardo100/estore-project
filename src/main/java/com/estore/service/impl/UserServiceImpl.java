package com.estore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estore.dto.request.UserRequest;
import com.estore.dto.response.UserResponse;
import com.estore.model.User;
import com.estore.repository.UserRepository;
import com.estore.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse cadastrar(UserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .nome(request.getNome())
                .tipo(request.getTipo())
                .build();
        return toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nome(user.getNome())
                .tipo(user.getTipo())
                .build();
    }
}
