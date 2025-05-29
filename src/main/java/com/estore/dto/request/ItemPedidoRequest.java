package com.estore.dto.request;

import lombok.Data;

@Data
public class ItemPedidoRequest {
    private Long produtoId;
    private int quantidade;
}
