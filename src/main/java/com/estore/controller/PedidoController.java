package com.estore.controller;

import com.estore.dto.request.PedidoRequest;
import com.estore.dto.response.PedidoResponse;
import com.estore.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> realizarPedido(
            @RequestBody PedidoRequest request,
            @RequestParam Long usuarioId) {
        PedidoResponse response = pedidoService.realizarPedido(request, usuarioId);
        return ResponseEntity.ok(response);
    }
}
