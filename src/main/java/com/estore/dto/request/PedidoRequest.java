package com.estore.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {
    private String enderecoEntrega;
    private List<ItemPedidoRequest> itens;
}
