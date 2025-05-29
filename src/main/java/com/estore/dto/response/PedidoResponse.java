package com.estore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PedidoResponse {
    private Long id;
    private String endereco;
    private BigDecimal valorTotal;
    private List<ItemPedidoResponse> itens;
}
