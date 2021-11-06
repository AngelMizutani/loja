package com.dev.loja.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.dev.loja.modelos.Categoria;
import com.dev.loja.modelos.Marca;
import com.dev.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    Page<Produto> findByDescricaoContaining(Pageable pageable, String descricao);
    Page<Produto> findByCategoria(Pageable pageable, Optional<Categoria> categoria);  
    Page<Produto> findByMarca(Pageable pageable, Optional<Marca> marca); 
}
