package com.estore.dto.request;

import com.estore.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;

    private String nome;

    private Role tipo;
}
