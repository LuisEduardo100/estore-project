package com.estore.service.impl;

import com.estore.dto.request.CategoriaRequest;
import com.estore.dto.response.CategoriaResponse;
import com.estore.model.Categoria;
import com.estore.repository.CategoriaRepository;
import com.estore.service.CategoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public CategoriaResponse criarCategoria(CategoriaRequest request) {
        if (categoriaRepository.existsByNome(request.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com o nome: " + request.getNome());
        }
        Categoria categoria = Categoria.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .build();
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return toResponse(savedCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse buscarCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + id));
        return toResponse(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponse atualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + id));

        if (!categoria.getNome().equals(request.getNome()) && categoriaRepository.existsByNome(request.getNome())) {
            throw new IllegalArgumentException("Já existe outra categoria com o nome: " + request.getNome());
        }

        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return toResponse(updatedCategoria);
    }

    @Override
    @Transactional
    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada com o ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .build();
    }
}