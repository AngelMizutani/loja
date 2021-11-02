package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.dev.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    List<Produto> findByDescricaoContaining(String descricao);
    List<Produto> findByCategoriaContaining(String categoria);
    List<Produto> findByMarcaContaining(String marca);
}
