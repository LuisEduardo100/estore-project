package com.estore.service;

import com.estore.dto.request.CategoriaRequest;
import com.estore.dto.response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    CategoriaResponse criarCategoria(CategoriaRequest request);

    CategoriaResponse buscarCategoriaPorId(Long id);

    List<CategoriaResponse> listarTodasCategorias();

    CategoriaResponse atualizarCategoria(Long id, CategoriaRequest request);

    void deletarCategoria(Long id);
}