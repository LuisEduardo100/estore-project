package com.estore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String marca;
    private String categoria;

    @Column(nullable = false)
    private BigDecimal preco;

    private String descricao;

    @ElementCollection
    @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "imagem")
    private List<String> imagens;

    @ElementCollection
    @CollectionTable(name = "produto_variacoes", joinColumns = @JoinColumn(name = "produto_id"))
    @MapKeyColumn(name = "chave")
    @Column(name = "valor")
    private Map<String, String> variacoes;
}
