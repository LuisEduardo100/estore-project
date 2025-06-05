package com.estore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.estore.model.StatusPedido;

@Data
@Builder
public class PedidoResponse {
    private Long id;
    private String endereco;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private List<ItemPedidoResponse> itens;
}
