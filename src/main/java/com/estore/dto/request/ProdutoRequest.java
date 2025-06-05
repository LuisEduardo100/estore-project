package com.estore.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequest {
    @NotBlank
    private String nome;

    private String marca;
    private String categoria;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal preco;

    private String descricao;

    private List<String> imagens;

    private Map<String, String> variacoes;
}
