package com.estore.service;

import java.util.List;

import com.estore.dto.request.PedidoRequest;
import com.estore.dto.response.PedidoResponse;
import com.estore.model.StatusPedido;

public interface PedidoService {
    PedidoResponse realizarPedido(PedidoRequest request);

    List<PedidoResponse> listarPedidosDoUsuario();

    PedidoResponse buscarPorId(Long id);

    List<PedidoResponse> listarTodos();

    void atualizarStatus(Long id, StatusPedido status);

    void cancelarPedido(Long id);
}
