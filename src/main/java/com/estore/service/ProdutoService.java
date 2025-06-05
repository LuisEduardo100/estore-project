package com.estore.service;

import com.estore.dto.request.ProdutoRequest;
import com.estore.dto.response.ProdutoResponse;

import java.util.List;

public interface ProdutoService {
    ProdutoResponse criar(ProdutoRequest request);

    List<ProdutoResponse> listarTodos();

    ProdutoResponse buscarPorId(Long id);

    ProdutoResponse atualizar(Long id, ProdutoRequest request);

    void deletar(Long id);
}
