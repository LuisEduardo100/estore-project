package com.estore.dto.response;

import com.estore.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String nome;
    private Role tipo;
}
