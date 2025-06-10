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
    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long categoriaId;

    private String marca;
    private String categoria;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal preco;

    private String descricao;

    @NotNull(message = "O estoque do produto é obrigatório.")
    @Min(value = 0, message = "O estoque não pode ser negativo.")
    private Integer estoque;

    private List<String> imagens;

    private Map<String, String> variacoes;
}
