package com.estore.service.impl;

import com.estore.dto.request.*;
import com.estore.dto.response.*;
import com.estore.model.*;
import com.estore.repository.*;
import com.estore.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;

    @Override
    public PedidoResponse realizarPedido(PedidoRequest request, Long usuarioId) {
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<ItemPedido> itens = request.getItens().stream()
                .map(item -> {
                    Produto produto = produtoRepository.findById(item.getProdutoId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getProdutoId()));

                    return ItemPedido.builder()
                            .produto(produto)
                            .quantidade(item.getQuantidade())
                            .precoUnitario(produto.getPreco())
                            .build();
                }).collect(Collectors.toList());

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .endereco(request.getEnderecoEntrega())
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
                .itens(pedido.getItens().stream().map(item -> ItemPedidoResponse.builder()
                        .produtoNome(item.getProduto().getNome())
                        .quantidade(item.getQuantidade())
                        .precoUnitario(item.getPrecoUnitario())
                        .subtotal(item.calcularSubtotal())
                        .build()).toList())
                .build();
    }
}
