package com.estore.service.impl;

import com.estore.dto.request.ProdutoRequest;
import com.estore.dto.response.ProdutoResponse;
import com.estore.model.Produto;
import com.estore.repository.ProdutoRepository;
import com.estore.service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = mapToEntity(request);
        return mapToResponse(produtoRepository.save(produto));
    }

    @Override
    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return mapToResponse(produto);
    }

    @Override
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produto.setNome(request.getNome());
        produto.setMarca(request.getMarca());
        produto.setCategoria(request.getCategoria());
        produto.setPreco(request.getPreco());
        produto.setDescricao(request.getDescricao());
        produto.setImagens(request.getImagens());
        produto.setVariacoes(request.getVariacoes());

        return mapToResponse(produtoRepository.save(produto));
    }

    @Override
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    private Produto mapToEntity(ProdutoRequest request) {
        return Produto.builder()
                .nome(request.getNome())
                .marca(request.getMarca())
                .categoria(request.getCategoria())
                .preco(request.getPreco())
                .descricao(request.getDescricao())
                .imagens(request.getImagens())
                .variacoes(request.getVariacoes())
                .build();
    }

    private ProdutoResponse mapToResponse(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .marca(produto.getMarca())
                .categoria(produto.getCategoria())
                .preco(produto.getPreco())
                .descricao(produto.getDescricao())
                .imagens(produto.getImagens())
                .variacoes(produto.getVariacoes())
                .build();
    }
}
