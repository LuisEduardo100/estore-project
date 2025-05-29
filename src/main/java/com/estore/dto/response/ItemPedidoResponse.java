package com.estore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemPedidoResponse {
    private String produtoNome;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
}
