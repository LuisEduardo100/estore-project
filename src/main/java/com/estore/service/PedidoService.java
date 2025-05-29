package com.estore.service;

import com.estore.dto.request.PedidoRequest;
import com.estore.dto.response.PedidoResponse;

public interface PedidoService {
    PedidoResponse realizarPedido(PedidoRequest request, Long usuarioId);
}
