// src/main/java/com/estore/dto/request/CategoriaRequest.java
package com.estore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CategoriaRequest {

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(max = 100, message = "O nome da categoria não pode exceder 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição da categoria não pode exceder 255 caracteres.")
    private String descricao;

}