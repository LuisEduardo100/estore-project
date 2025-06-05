package com.estore.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponse {
    private Long id;
    private String nome;
    private String marca;
    private String categoria;
    private BigDecimal preco;
    private String descricao;
    private List<String> imagens;
    private Map<String, String> variacoes;
}
