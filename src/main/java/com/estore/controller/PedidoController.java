package com.estore.controller;

import com.estore.dto.request.AtualizarStatusRequest;
import com.estore.dto.request.PedidoRequest;
import com.estore.dto.response.PedidoResponse;
import com.estore.service.PedidoService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> realizarPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.realizarPedido(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meus")
    public ResponseEntity<List<PedidoResponse>> listarPedidosUsuario() {
        return ResponseEntity.ok(pedidoService.listarPedidosDoUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest request) {
        pedidoService.atualizarStatus(id, request.getStatus());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
