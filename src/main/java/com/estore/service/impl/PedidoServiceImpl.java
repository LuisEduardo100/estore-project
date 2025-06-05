package com.estore.service.impl;

import com.estore.dto.request.*;
import com.estore.dto.response.*;
import com.estore.model.*;
import com.estore.repository.*;
import com.estore.service.PedidoService;
import com.estore.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

        private final PedidoRepository pedidoRepository;
        private final ProdutoRepository produtoRepository;
        private final UserService userService;

        @Override
        public PedidoResponse realizarPedido(PedidoRequest request) {
                User usuario = userService.getUsuarioAutenticado();

                List<ItemPedido> itens = request.getItens().stream()
                                .map(item -> {
                                        Produto produto = produtoRepository.findById(item.getProdutoId())
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Produto não encontrado: "
                                                                                        + item.getProdutoId()));
                                        return ItemPedido.builder()
                                                        .produto(produto)
                                                        .quantidade(item.getQuantidade())
                                                        .precoUnitario(produto.getPreco())
                                                        .build();
                                }).toList();

                Pedido pedido = Pedido.builder()
                                .usuario(usuario)
                                .endereco(request.getEnderecoEntrega())
                                .status(StatusPedido.REALIZADO)
                                .itens(itens)
                                .build();

                pedido.calcularValorTotal();

                Pedido pedidoSalvo = pedidoRepository.save(pedido);

                return toResponse(pedidoSalvo);
        }

        private PedidoResponse toResponse(Pedido pedido) {
                return PedidoResponse.builder()
                                .id(pedido.getId())
                                .endereco(pedido.getEndereco())
                                .valorTotal(pedido.getValorTotal())
                                .status(pedido.getStatus())
                                .itens(pedido.getItens().stream().map(item -> ItemPedidoResponse.builder()
                                                .produtoNome(item.getProduto().getNome())
                                                .quantidade(item.getQuantidade())
                                                .precoUnitario(item.getPrecoUnitario())
                                                .subtotal(item.calcularSubtotal())
                                                .build()).toList())
                                .build();
        }

        @Override
        public List<PedidoResponse> listarPedidosDoUsuario() {
                User usuario = userService.getUsuarioAutenticado();
                return pedidoRepository.findByUsuario(usuario).stream().map(this::toResponse).toList();
        }

        @Override
        public PedidoResponse buscarPorId(Long id) {
                Pedido pedido = pedidoRepository.findById(id).orElseThrow();
                User usuario = userService.getUsuarioAutenticado();
                if (!pedido.getUsuario().getId().equals(usuario.getId()) && usuario.getTipo() != Role.ADMIN) {
                        throw new AccessDeniedException("Acesso negado ao pedido");
                }
                return toResponse(pedido);
        }

        @Override
        public List<PedidoResponse> listarTodos() {
                User usuario = userService.getUsuarioAutenticado();
                if (usuario.getTipo() != Role.ADMIN) {
                        throw new AccessDeniedException("Acesso negado");
                }
                return pedidoRepository.findAll().stream().map(this::toResponse).toList();
        }

        @Override
        public void atualizarStatus(Long id, StatusPedido status) {
                User usuario = userService.getUsuarioAutenticado();
                if (usuario.getTipo() != Role.ADMIN) {
                        throw new AccessDeniedException("Apenas administradores podem atualizar o status");
                }
                Pedido pedido = pedidoRepository.findById(id).orElseThrow();
                pedido.setStatus(status);
                pedidoRepository.save(pedido);
        }

        @Override
        public void cancelarPedido(Long id) {
                Pedido pedido = pedidoRepository.findById(id).orElseThrow();
                User usuario = userService.getUsuarioAutenticado();
                if (!pedido.getUsuario().getId().equals(usuario.getId()) && usuario.getTipo() != Role.ADMIN) {
                        throw new AccessDeniedException("Acesso negado ao pedido");
                }
                pedido.setStatus(StatusPedido.CANCELADO);
                pedidoRepository.save(pedido);
        }

}
