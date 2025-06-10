// src/main/java/com/estore/config/JwtAuthFilter.java
package com.estore.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = null;
        Claims claims = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            claims = jwtService.extractAllClaims(jwt);
            userEmail = claims.getSubject();
        } catch (ExpiredJwtException e) {
            // Logar a expiração do token, mas não impedir o filtro de continuar
            // Permite que Spring Security lide com Unauthorized ou Forbidden posteriormente
            System.err.println("Token JWT expirado para requisição em: " + request.getRequestURI());
        } catch (Exception e) {
            // Logar outros erros de token, mas não impedir o filtro de continuar
            System.err
                    .println("Erro de token JWT para requisição em " + request.getRequestURI() + ": " + e.getMessage());
        }

        // Apenas processa se o email foi extraído e não há autenticação no contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            } catch (Exception e) {
                // Logar erro ao carregar UserDetails (usuário não encontrado no DB)
                System.err.println("Erro ao carregar UserDetails para " + userEmail + ": " + e.getMessage());
                // Não autenticar se UserDetails não puder ser carregado
            }

            if (userDetails != null && jwtService.isTokenValid(jwt, userDetails)) {
                Collection<? extends GrantedAuthority> authorities = null;

                // Tenta obter as roles do JWT. Se não houver, usa as do UserDetails (DB).
                Object rolesClaim = claims != null ? claims.get("roles") : null;
                if (rolesClaim instanceof List) {
                    List<String> rolesList = (List<String>) rolesClaim;
                    authorities = rolesList.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                } else {
                    // Fallback para authorities carregadas do UserDetailsService se a claim "roles"
                    // não estiver presente ou estiver em formato inválido
                    System.err.println(
                            "Claim 'roles' não encontrada ou em formato inesperado no JWT. Usando authorities do UserDetailsService.");
                    authorities = userDetails.getAuthorities();
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // A credencial (senha) não é necessária aqui no token de autenticação
                        authorities // IMPORTANTÍSSIMO: Usar as authorities extraídas do token ou do UserDetails
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    // shouldNotFilter geralmente é usado para ignorar completamente o filtro para
    // certas URLs.
    // É mais seguro deixar o SecurityConfig lidar com permitAll() para evitar
    // duplicação ou conflitos.
    // Removi PUBLIC_URLS daqui e vou confiar apenas no SecurityConfig.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Por padrão, este filtro deve ser aplicado a todas as requisições.
        // As regras de permitAll() devem ser definidas no SecurityConfig.
        // Exceção comum: requisições OPTIONS (pre-flight CORS)
        return request.getMethod().equals(HttpMethod.OPTIONS.name());
    }
}