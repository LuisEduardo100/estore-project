package com.estore.config;

import com.estore.model.User;
import com.estore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getSenha())
                .roles(user.getTipo().name()) // exemplo: ADMIN ou CLIENTE
                .build();
    }
}
